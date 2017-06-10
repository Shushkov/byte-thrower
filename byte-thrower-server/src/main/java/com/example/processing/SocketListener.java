package com.example.processing;

import com.example.domain.Answer;
import com.example.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Sergey Shushkov on 11.06.2017.
 * ClearScale Java Team
 */
@Component
public class SocketListener {

    private final static Logger logger = LoggerFactory.getLogger(SocketListener.class);

    private int port;

    public SocketListener(@Value("${port}") int port, @Autowired MessageProcessor messageProcessor) {
        this.port = port;
        this.messageProcessor = messageProcessor;
    }


    private MessageProcessor messageProcessor;

    public void listen(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);

            logger.info("listening port: {}", port);

            while (true) {
                final Socket clientSocket = serverSocket.accept();

                Thread  thread = new Thread(() -> {
                    try {
                        logger.debug("incoming connection");

                        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                        Message message = (Message) in.readObject();

                        logger.debug("Read message: " + message);

                        Answer answer = messageProcessor.proccessMessage(message);

                        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

                        out.writeObject(answer);

                        out.flush();

                    } catch (IOException | ClassNotFoundException e) {
                        logger.error("", e);
                    } finally {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            logger.error(" can't close client socket", e);
                        }
                    }
                });

                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
