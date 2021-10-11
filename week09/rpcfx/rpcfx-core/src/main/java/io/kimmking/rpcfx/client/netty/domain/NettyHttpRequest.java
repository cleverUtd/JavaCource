package io.kimmking.rpcfx.client.netty.domain;

import io.kimmking.rpcfx.util.ModuleUtils;
import io.netty.handler.codec.http.HttpMethod;
import lombok.Data;

@Data
public class NettyHttpRequest {

    /**
     * 默认post
     */
    HttpMethod httpMethod = HttpMethod.POST;

    private Object body;
    /**
     * 包含了全部信息，如：
     * http://192.168.19.102:8080/BOL_WebService/ElectronicFootRingAlarm.do
     */
    private String fullUrl;

    /**
     * 不包含url前面的协议、ip、端口，如：
     * /BOL_WebService/ElectronicFootRingAlarm.do
     */
    private String uri;

    String port;

    public NettyHttpRequest(String fullUrl, Object body) {
        this.body = body;
        this.fullUrl = fullUrl;
        port = ModuleUtils.extractPort(fullUrl);

        int index = fullUrl.indexOf(port);
        this.uri = fullUrl.substring(index + port.length());
    }
}
