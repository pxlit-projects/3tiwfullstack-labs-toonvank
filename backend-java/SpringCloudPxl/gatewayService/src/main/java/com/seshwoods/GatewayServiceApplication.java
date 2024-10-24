package com.seshwoods;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServiceApplication
{
    public static void main( String[] args )
    {
        new SpringApplicationBuilder(GatewayServiceApplication.class).run(args);
    }
}
