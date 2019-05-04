package com.andy.consumer.web.client;

import com.andy.consumer.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * Feign根据服务名称去Eureka服务列表拉取对应的服务
 * 拉取服务之后根据ribbon实现负载均衡
 * 紧接着向user-service/user/id发起请求，传递参数id
 */

@FeignClient(value = "user-service", fallback = UserClientFallback.class)
public interface UserClient {
    @GetMapping("/user/{id}")
    User queryById(@PathVariable("id") Long id);
}
