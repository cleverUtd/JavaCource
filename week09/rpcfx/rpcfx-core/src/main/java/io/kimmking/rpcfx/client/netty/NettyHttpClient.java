package io.kimmking.rpcfx.client.netty;

import io.kimmking.rpcfx.client.netty.domain.NettyHttpRequest;
import io.kimmking.rpcfx.client.netty.domain.NettyHttpRequestContext;
import io.kimmking.rpcfx.client.netty.domain.NettyHttpResponse;
import io.kimmking.rpcfx.client.netty.handler.HttpJsonRequestEncoder;
import io.kimmking.rpcfx.client.netty.handler.HttpResponseHandler;
import io.kimmking.rpcfx.util.ModuleUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyHttpClient implements Closeable {

    /**
     * netty client bootstrap
     */
    private static final DefaultEventLoop NETTY_RESPONSE_PROMISE_NOTIFY_EVENT_LOOP =  new DefaultEventLoop();

    private String host;
    private String port;
    private Channel channel;

    public NettyHttpClient(final String url) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).build();
        this.host = uriComponents.getHost();
        this.port = String.valueOf(uriComponents.getPort());
    }

    public void initConnection() {
        log.info("initConnection starts...");

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                // http客户端编解码器，包括了客户端http请求编码，http响应的解码
                ch.pipeline().addLast(new HttpClientCodec());
                // 把多个HTTP请求中的数据组装成一个
                ch.pipeline().addLast(new HttpObjectAggregator(65536));
                // 用于处理大数据流
                ch.pipeline().addLast(new ChunkedWriteHandler());
                /**
                 * 发送业务数据前，进行json编码
                 */
                ch.pipeline().addLast(new HttpJsonRequestEncoder());
                ch.pipeline().addLast(new HttpResponseHandler());
            }
        });

        ChannelFuture future = b.connect(this.host, Integer.parseInt(this.port));
        // 等待连接成功
        boolean ret = future.awaitUninterruptibly(2000, TimeUnit.MILLISECONDS);
        boolean bIsSuccess = ret && future.isSuccess();
        if (!bIsSuccess) {
            // 不成功抛异常
            throw new RuntimeException("连接失败");
        }
        this.cleanOldChannelAndCancelReconnect(future, this.channel);
    }

    private void cleanOldChannelAndCancelReconnect(ChannelFuture future, Channel oldChannel) {
        /**
         * 连接成功，关闭旧的channel，再用新的channel赋值给field
         */
        try {
            if (oldChannel != null) {
                try {
                    log.info("Close old netty channel " + oldChannel);
                    oldChannel.close();
                } catch (Exception e) {
                    log.error("e:{}", e);
                }
            }
        } finally {
            /**
             * 新channel覆盖field
             */
            NettyHttpClient.this.channel = future.channel();
        }
    }

    public static final AttributeKey<NettyHttpRequestContext> CURRENT_REQ_BOUND_WITH_THE_CHANNEL =
            AttributeKey.valueOf("CURRENT_REQ_BOUND_WITH_THE_CHANNEL");

    public NettyHttpResponse doPost(NettyHttpRequest request) {
        return doHttpRequest(request);
    }

    public NettyHttpResponse doGet(NettyHttpRequest request) {
        request.setHttpMethod(HttpMethod.GET);
        return doHttpRequest(request);
    }

    private NettyHttpResponse doHttpRequest(NettyHttpRequest request) {
        Promise<NettyHttpResponse> defaultPromise = NETTY_RESPONSE_PROMISE_NOTIFY_EVENT_LOOP.newPromise();
        NettyHttpRequestContext context = new NettyHttpRequestContext(request, defaultPromise);
        channel.attr(CURRENT_REQ_BOUND_WITH_THE_CHANNEL).set(context);

        ChannelFuture channelFuture = channel.writeAndFlush(request);
        channelFuture.addListener(future -> log.info("请求发送完成. {}", Thread.currentThread().getName()));

        return get(defaultPromise);
    }

    public <V> V get(Promise<V> future) {
        if (!future.isDone()) {
            CountDownLatch l = new CountDownLatch(1);
            future.addListener(future1 -> {
                log.info("received response,listener is invoked");
                if (future1.isDone()) {
                    // io线程会回调该listener
                    l.countDown();
                }
            });

            boolean interrupted = false;
            if (!future.isDone()) {
                try {
                    l.await(4, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    log.error("e:{}", e);
                    interrupted = true;
                }

            }

            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }

        if (future.isSuccess()) {
            return future.getNow();
        }
        log.error("wait result time out ");
        return null;
    }

    public static AttributeKey<Boolean> ACTIVE_CLOSE = AttributeKey.valueOf("ACTIVE_CLOSE");
    @Override
    public void close() throws IOException {
        if (channel != null) {
            channel.attr(ACTIVE_CLOSE).set(true);
            channel.close().syncUninterruptibly();
        }
    }
}
