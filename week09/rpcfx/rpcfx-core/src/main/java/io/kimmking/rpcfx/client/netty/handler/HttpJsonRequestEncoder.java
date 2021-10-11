package io.kimmking.rpcfx.client.netty.handler;

import java.util.List;

import io.kimmking.rpcfx.client.netty.domain.NettyHttpRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpJsonRequestEncoder extends MessageToMessageEncoder<NettyHttpRequest> {


    @Override
    protected void encode(final ChannelHandlerContext ctx, final NettyHttpRequest nettyHttpRequest, final List<Object> out) throws Exception {
        FullHttpRequest request = null;
        if (nettyHttpRequest.getHttpMethod() == HttpMethod.POST) {
            ByteBuf encodeBuf = Unpooled.copiedBuffer((CharSequence) nettyHttpRequest.getBody(), UTF_8);
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,
                    HttpMethod.POST, nettyHttpRequest.getUri(), encodeBuf);

            HttpUtil.setContentLength(request, encodeBuf.readableBytes());
        } else if (nettyHttpRequest.getHttpMethod() == HttpMethod.GET) {
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,
                    HttpMethod.GET, nettyHttpRequest.getUri());
        } else {
            throw new RuntimeException();
        }

        populateHeaders(ctx, request);

        out.add(request);
    }

    private void populateHeaders(ChannelHandlerContext ctx, FullHttpRequest request) {
        /**
         * headers 设置
         */
        HttpHeaders headers = request.headers();
        headers.set(HttpHeaderNames.HOST, ctx.channel().remoteAddress().toString().substring(1));
        headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        headers.set(HttpHeaderNames.CONTENT_TYPE,
                "application/json");
        /**
         * 设置我方可以接收的
         */
        headers.set(HttpHeaderNames.ACCEPT_ENCODING,
                HttpHeaderValues.GZIP.toString() + ','
                        + HttpHeaderValues.DEFLATE.toString());

        headers.set(HttpHeaderNames.ACCEPT_CHARSET,
                "utf-8,ISO-8859-1;q=0.7,*;q=0.7");
        headers.set(HttpHeaderNames.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.set(HttpHeaderNames.ACCEPT, "*/*");
        /**
         * 设置agent
         */
        headers.set(HttpHeaderNames.USER_AGENT,
                "Netty xml Http Client side");
    }
}
