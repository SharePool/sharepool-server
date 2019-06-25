package com.sharepool.sharepoolanalytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SharePoolAnalyticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharePoolAnalyticsApplication.class, args);
    }

}
