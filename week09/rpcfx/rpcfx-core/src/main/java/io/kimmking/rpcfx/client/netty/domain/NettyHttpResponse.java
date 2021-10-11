package io.kimmking.rpcfx.client.netty.domain;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NettyHttpResponse {

    /**
     * 响应码，比如200
     */
    private HttpResponseStatus httpResponseStatus;

    /**
     * 消息体,utf-8解码
     */
    private String body;



    public static NettyHttpResponse successResponse(String body) {
        return new NettyHttpResponse(HttpResponseStatus.OK, body);
    }
}
