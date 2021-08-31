package proxy.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UserServiceProxy implements InvocationHandler {
    private Object target;

    public UserServiceProxy() {
    }

    public UserServiceProxy(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        System.out.println("begin " + methodName);

        Object result = method.invoke(target, args);

        System.out.println("finish " + methodName);
        return result;
    }
}
