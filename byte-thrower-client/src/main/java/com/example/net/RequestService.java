package com.example.net;

import com.example.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Sergey Shushkov on 10.06.2017.
 * ClearScale Java Team
 */
@Service
public class RequestService implements IRequsetService{

    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    private String host;

    private int port;

    public RequestService(@Value("${host}") String host, @Value("${port}") int port) {
        this.host = host;
        this.port = port;
    }

    private static volatile int messageId = 1;

    @Override
    public void sendMessage(String serviceName, String methodName, Class[] argType, Object... args) {

        Message message = new Message(serviceName, methodName, args, messageId++, argType);

        Request request = new Request(message, host, port);

        Thread thread = new Thread(request);

        thread.start();
    }
}
