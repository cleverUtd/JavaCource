package io.kimmking.rpcfx.client.netty.handler;

import io.kimmking.rpcfx.client.netty.NettyHttpClient;
import io.kimmking.rpcfx.client.netty.domain.NettyHttpRequestContext;
import io.kimmking.rpcfx.client.netty.domain.NettyHttpResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpResponseHandler extends SimpleChannelInboundHandler<FullHttpResponse> {
    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final FullHttpResponse fullHttpResponse) throws Exception {
        String s = fullHttpResponse.content().toString(CharsetUtil.UTF_8);

        NettyHttpResponse nettyHttpResponse = NettyHttpResponse.successResponse(s);
        NettyHttpRequestContext nettyHttpRequestContext = ctx.channel().attr(NettyHttpClient.CURRENT_REQ_BOUND_WITH_THE_CHANNEL).get();

        log.info("req url:{},params:{},resp:{}",
                nettyHttpRequestContext.getNettyHttpRequest().getFullUrl(),
                nettyHttpRequestContext.getNettyHttpRequest().getBody(),
                nettyHttpResponse);
        Promise<NettyHttpResponse> promise = nettyHttpRequestContext.getDefaultPromise();
        promise.setSuccess(nettyHttpResponse);
    }
}
