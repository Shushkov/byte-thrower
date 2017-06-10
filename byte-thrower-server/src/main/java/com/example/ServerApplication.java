package com.example;

import com.example.processing.SocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by Sergey Shushkov on 10.06.2017.
 * ClearScale Java Team
 */
@SpringBootApplication(scanBasePackages = "com.example")
public class ServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args){
        ConfigurableApplicationContext ctx = SpringApplication.run(ServerApplication.class, args);

        SocketListener socketListener = ctx.getBean(SocketListener.class);

        socketListener.listen();
    }
}
