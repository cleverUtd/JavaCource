package io.kimmking.rpcfx.demo.consumer;

import io.kimmking.rpcfx.api.Filter;
import io.kimmking.rpcfx.api.LoadBalancer;
import io.kimmking.rpcfx.api.Router;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.client.Rpcfx;
import io.kimmking.rpcfx.client.RpcfxByteBuddy;
import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Slf4j
public class RpcfxClientApplication {

    // 二方库
    // 三方库 lib
    // nexus, userserivce -> userdao -> user
    //

    public static void main(String[] args) {

        UserService userService = RpcfxByteBuddy.create(UserService.class, "http://127.0.0.1:8081/");
        if (userService != null) {
            User user = userService.findById(1);
            log.info("find user id=1 from server: {}", user.getName());
        }

        OrderService orderService = Rpcfx.create(OrderService.class, "http://127.0.0.1:8081/");
        Order order = orderService.findOrderById(1992129);
        log.info("find order name= {}, amount= {}", order.getName(), order.getAmount());
    }

    private static class TagRouter implements Router {
        @Override
        public List<String> route(List<String> urls) {
            return urls;
        }
    }

    private static class RandomLoadBalancer implements LoadBalancer {
        @Override
        public String select(List<String> urls) {
            return urls.get(0);
        }
    }

    @Slf4j
    private static class CuicuiFilter implements Filter {
        @Override
        public boolean filter(RpcfxRequest request) {
//			log.info("filter {} -> {}", this.getClass().getName(), request.toString());
            return true;
        }
    }
}



