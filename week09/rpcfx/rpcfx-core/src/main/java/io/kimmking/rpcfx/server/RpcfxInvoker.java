package io.kimmking.rpcfx.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.kimmking.rpcfx.aop.ExceptionHandler;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class RpcfxInvoker {

    private final RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver) {
        this.resolver = resolver;
    }

    @ExceptionHandler
    public <T> RpcfxResponse invoke(RpcfxRequest<T> request) throws InvocationTargetException, IllegalAccessException {
        RpcfxResponse response = new RpcfxResponse();
        Class<T> serviceClass = request.getServiceClass();
        // 作业1：改成泛型和反射
        T service = resolver.resolve(serviceClass);

        Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
        Object result = method.invoke(service, request.getParams()); // dubbo, fastjson,
        // 两次json序列化能否合并成一个
        response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
        response.setStatus(true);
        return response;
    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }

}
