package com.example.domain;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Sergey Shushkov on 10.06.2017.
 * ClearScale Java Team
 */
public class Message implements Serializable{
    private String serviceName;
    private String methodName;
    private Object[] args;
    private int id;
    private Class[] argsType;

    public Message() {
    }

    public Message(String serviceName, String methodName, Object[] args, int id, Class[] argsType) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.args = args;
        this.id = id;
        this.argsType = argsType;
    }

    public Class[] getArgsType() {
        return argsType;
    }

    public void setArgsType(Class[] argsType) {
        this.argsType = argsType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object [] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + this.id + '\'' +
                "serviceName='" + this.serviceName + '\'' +
                ", methodName='" + this.methodName + '\'' +
                ", args=" + Arrays.toString(this.args) +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
