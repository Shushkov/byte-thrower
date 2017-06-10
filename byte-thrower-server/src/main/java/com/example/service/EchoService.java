package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sergey Shushkov on 10.06.2017.
 * ClearScale Java Team
 */
public class EchoService {

    private static final Logger logger = LoggerFactory.getLogger(EchoService.class);

    public String testEcho(String arg1){
        logger.debug("incoming args: {}", arg1);
        return arg1;
    }

}
