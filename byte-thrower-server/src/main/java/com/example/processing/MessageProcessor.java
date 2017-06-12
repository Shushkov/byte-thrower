package com.example.processing;

import com.example.ServerApplication;
import com.example.domain.Answer;
import com.example.domain.Error;
import com.example.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by Sergey Shushkov on 10.06.2017.
 * ClearScale Java Team
 *
 * service with determine needed service and its method by given Message {@link Message}
 */
@Component
public class MessageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    private HashMap<String, Object> classHashMap = new HashMap<>();

    @Value("${service.prop.file}")
    private String propFilename;

    public String getPropFilename() {
        return propFilename;
    }

    public void setPropFilename(String propFilename) {
        this.propFilename = propFilename;
    }

    @PostConstruct
    public void init() {
        Properties prop = new Properties();

        InputStream propStream = ServerApplication.class.getClassLoader().getResourceAsStream(propFilename);

        try {
            prop.load(propStream);
        } catch (IOException e) {
            throw new RuntimeException("can't load server services property file", e);
        }

        for(String label: prop.stringPropertyNames()){
            String className = prop.getProperty(label);
            try {
                classHashMap.put(label, Class.forName(className).newInstance());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                logger.error("can't load class " + className, e);
            }
        }

        try {
            propStream.close();
        } catch (IOException e) {
            logger.error("can't close property stream", e);
        }

        logger.debug(classHashMap.toString());
    }

    /**
     *
     * determine needed service and its method by given Message {@link Message}
     * and run it with given parameters
     * */
    public Answer proccessMessage(Message message){

        if (Objects.isNull(message) || Objects.isNull(message.getMethodName()) || Objects.isNull(message.getServiceName()))
            return processError("unrecognized message",null, -1);

        Object service =  classHashMap.get(message.getServiceName());

        if (service == null) {
            return processError("unsupported service label" + message.getServiceName(),null, message.getId());
        }

        try {
            Method method;

            if (message.getArgs() == null || message.getArgs().length == 0) {
                method = service.getClass().getDeclaredMethod(message.getMethodName(), Void.class);

                method.invoke(service);

                return new Answer(message.getId(), null);
            } else {

                method = service.getClass().getDeclaredMethod(message.getMethodName(), message.getArgsType());


                Object result = method.invoke(service, message.getArgs());

                return new Answer(message.getId(), result);
            }
        } catch (NoSuchMethodException e) {
            return processError("unsupported service method " + message.getMethodName(), e, message.getId());
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            return processError("can't invoke target method " + message.getMethodName(), e, message.getId());
        }
    }

    private Answer processError(String msg, Throwable e, int id){
        logger.error(msg, e);
        return new Answer(id, new Error(msg));
    }
}
