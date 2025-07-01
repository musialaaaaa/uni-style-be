package org.example.uni_style_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UniStyleBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniStyleBeApplication.class, args);
    }

}
