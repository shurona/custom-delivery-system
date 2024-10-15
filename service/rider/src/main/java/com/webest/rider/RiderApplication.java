package com.webest.rider;

import static com.webest.web.common.CommonStaticVariable.BASE_PACKAGE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
public class RiderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RiderApplication.class, args);
    }

}
