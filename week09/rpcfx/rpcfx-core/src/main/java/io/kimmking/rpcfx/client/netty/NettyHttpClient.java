package io.kimmking.rpcfx.client.netty;

import java.util.concurrent.TimeUnit;

import io.kimmking.rpcfx.client.netty.handler.HttpJsonRequestEncoder;
import io.kimmking.rpcfx.client.netty.handler.HttpResponseHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyHttpClient {

    private String host;
    private int port;
    private Channel channel;

    public NettyHttpClient(final String host, final int port) {
        this.host = host;
        this.port = port;
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

        ChannelFuture future = b.connect(this.host, this.port);
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
}
