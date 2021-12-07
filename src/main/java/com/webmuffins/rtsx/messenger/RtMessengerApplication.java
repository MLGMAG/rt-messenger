package com.webmuffins.rtsx.messenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RtMessengerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RtMessengerApplication.class, args);
    }

}
