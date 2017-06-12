package com.example.domain;

import java.io.Serializable;

/**
 * Created by Sergey Shushkov on 10.06.2017.
 * ClearScale Java Team
 *
 * class present error resoult for message {@link Message} processing
 */
public class Error implements Serializable {
    private String msg;

    public Error() {
    }

    public Error(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Error{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
