package com.example;

import com.example.net.IRequsetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * Created by Sergey Shushkov on 10.06.2017.
 * ClearScale Java Team
 *
 * Spring Boot start application
 */
@SpringBootApplication(scanBasePackages = "com.example")
public class ClientApplication {

    private static final Logger logger = LoggerFactory.getLogger(ClientApplication.class);

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(ClientApplication.class, args);
        logger.debug("application started");

        IRequsetService requsetService = ctx.getBean(IRequsetService.class);

        requsetService.sendMessage("echo", "testEcho", new Class[]{ String.class}, "test");
        requsetService.sendMessage("echo", "brokenEcho", new Class[]{ String.class, String.class}, "test" , "test");
        requsetService.sendMessage("ech2o", "test2", new Class[]{ String.class, String.class}, "test" , "test");
        requsetService.sendMessage("SumService", "sum", new Class[]{ Integer.class, Integer.class}, 4, 2);
        requsetService.sendMessage(null, null, null);

        Thread.currentThread().join(5_000);
    }


}
