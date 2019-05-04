package com.andy.consumer.web;

import com.andy.consumer.pojo.MainResult;
import com.andy.consumer.pojo.User;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("/consumer")
@DefaultProperties(defaultFallback = "defaultFallback")//使用通用的降级方法
@Slf4j
public class ConsumerController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
//    @Autowired
//    private RibbonLoadBalancerClient client;

    @GetMapping("/{id}")
//    @HystrixCommand(fallbackMethod = "queryAllUserFallback")
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "50000")//自定義超時時長
//    })
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "60"),
    })
    public MainResult queryAllUser(@PathVariable("id") Long id){
        MainResult result = new MainResult();
        //根据服务id取出ip和端口
//        List<ServiceInstance> instances = discoveryClient.getInstances("user_service");
//        ServiceInstance instance = instances.get(0);
//        String url = "http://"+instance.getHost()+":"+instance.getPort()+"/user/"+id;
        //随机、轮询、hash
        // 默认轮询
//        ServiceInstance instance = client.choose("user_service");
//        String url = "http://"+instance.getHost()+":"+instance.getPort()+"/user/"+id;

        if(id%2==0){
            throw new RuntimeException("抛异常");
        }
        String url = "http://user-service/user/"+id;
        log.info("url:{}",url);
//        String url2 = "http://USER_SERVICE/user/"+id;
        User user = restTemplate.getForObject(url, User.class);
        result.setData(user);
        return result;
    }

    public MainResult defaultFallback(){
        return new MainResult("服务器忙");
    }

    public static void main(String[] args) throws URISyntaxException {
        URI uri = new URI("http://user-service/user/1");
        String host = uri.getHost();
        System.out.println("host = " + host);
    }

    @RequestMapping("/queryByIds")
    public List<User> queryAllUser(String ids){
        //根据服务id取出ip和端口
        String[] idArray = ids.split(",");
        List idList = CollectionUtils.arrayToList(idArray);
        List<ServiceInstance> instances = discoveryClient.getInstances("user_service");
        ServiceInstance instance = instances.get(0);
        ArrayList<User> users = new ArrayList<>();
        idList.forEach(new Consumer<String>() {
            @Override
            public void accept(String id) {
                String url = "http://"+instance.getHost()+":"+instance.getPort()+"/user/"+id;
                log.info("url:{}",url);
                User user = restTemplate.getForObject(url, User.class);
                users.add(user);
            }
        });
        return users;
    }

}
