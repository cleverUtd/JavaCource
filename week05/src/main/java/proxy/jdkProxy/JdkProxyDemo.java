package proxy.jdkProxy;

import proxy.UserService;
import proxy.UserServiceImpl;

import java.lang.reflect.Proxy;

public class JdkProxyDemo {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService = (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(), userService.getClass().getInterfaces(), new UserServiceProxy(userService));

        System.out.println(userService.getName(1));

        System.out.println(userService.getAge(1));
    }
}
