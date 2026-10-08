package org.example;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@SpringBootApplication
@EnableEurekaClient
@MapperScan("org.example.mapper")
public class ServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ServiceApplication.class,args);
    }
}
