package com.example.net;

import com.example.domain.Answer;
import com.example.domain.Error;
import com.example.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * Created by Sergey Shushkov on 10.06.2017.
 * ClearScale Java Team
 */
public class Request implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    private final Message message;
    private final String host;
    private final int port;

    public Request(Message message, String host, int port) {
        this.message = message;
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {


        logger.debug("start");

        try (Socket socket = new Socket(host, port)) {

            socket.setSoTimeout(5_000);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            objectOutputStream.writeObject(message);

            objectOutputStream.flush();

            ObjectInputStream  objectInputStream = new ObjectInputStream(socket.getInputStream());

            Answer answer = (Answer) objectInputStream.readObject();

            if ((answer != null)){
                if ((answer.getResult() instanceof Error))
                    logger.error(answer.getResult().toString());
                else
                    logger.info("response {}", answer);
            }

        } catch (IOException | ClassNotFoundException exc){
            logger.error("", exc);
        }
    }

}
