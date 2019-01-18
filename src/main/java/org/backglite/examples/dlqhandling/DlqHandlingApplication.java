package org.backglite.examples.dlqhandling;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = "classpath*:**/spring/dlqh-*.xml")
@EnableRabbit
public class DlqHandlingApplication {
    public static void main(String[] args) {
        SpringApplication.run(DlqHandlingApplication.class, args);
    }
}

