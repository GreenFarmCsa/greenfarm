package com.callforcode.greenfarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
public class GreenFarmApplication {
    // CHECKSTYLE:OFF
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(GreenFarmApplication.class);
        application.run(args);
    }
   // CHECKSTYLE:ON

}
