package io.kimmking.rpcfx.client.netty.domain;

import io.netty.util.concurrent.Promise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NettyHttpRequestContext {
    /**
     * 当前的http请求
     */
    NettyHttpRequest nettyHttpRequest;

    /**
     * 主线程发送完请求后，在该promise上等待
     */
    Promise<NettyHttpResponse> defaultPromise;
}
