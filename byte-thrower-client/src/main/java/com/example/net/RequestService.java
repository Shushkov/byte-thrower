package com.example.net;

import com.example.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Sergey Shushkov on 10.06.2017.
 * ClearScale Java Team
 *
 * Service map passed parameters to Message {@link Message} class
 * and send it via Request {@link Request}
 *
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

    /**
     * @param serviceName - target service label name
     * @param methodName - target service method name
     * @param argType - massive of arguments class for service method
     * @param args - arguments for target method
     **/
    @Override
    public void sendMessage(String serviceName, String methodName, Class[] argType, Object... args) {

        Message message = new Message(serviceName, methodName, args, messageId++, argType);

        Request request = new Request(message, host, port);

        Thread thread = new Thread(request);

        thread.start();
    }
}
