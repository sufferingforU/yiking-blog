package com.yiking.blog;

//import com.spring4all.swagger.EnableSwagger2Doc;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@EnableSwagger2Doc
public class BlogServerMain8003 {
    public static void main(String[] args) {
        SpringApplication.run(BlogServerMain8003.class, args);
    }
}
