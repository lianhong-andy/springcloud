package com.andy.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication

/**
 * 发现一个标准的Eureka服务必须要引入
 * @EnableCircuitBreaker
 * @EnableDiscoveryClient
 * @SpringBootApplication
 * 三个依赖，所以springboot提供了一个新的注解@SpringCloudApplication，里面已经包含了这三个注解了
 */
@SpringCloudApplication
@EnableFeignClients
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }

    /**
     * 加了@LoadBalanced注解，会内置一个拦截器去连接restTemplate请求
     * @return
     */
    /**有了feign之后就不再需要restTemplate了，使用feign之后，底层使用动态代理自动帮你封装http请求*/
//    @Bean
//    @LoadBalanced
//    public RestTemplate getRestTemplate(){
//        return new RestTemplate();
//    }
}
