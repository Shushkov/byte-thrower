package com.example.net;

/**
 * Created by Sergey Shushkov on 10.06.2017.
 * ClearScale Java Team
 */
public interface IRequsetService {
    void sendMessage(String serviceName, String methodName, Class[] argType, Object... args);
}
