package com.sebastian.endpoint;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.sebastian.demo, com.sebastian.controller")
public class ProductTestAppConfig {

}
