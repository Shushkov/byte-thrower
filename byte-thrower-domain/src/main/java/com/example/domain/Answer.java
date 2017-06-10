package com.example.domain;

import java.io.Serializable;

/**
 * Created by Sergey Shushkov on 10.06.2017.
 * ClearScale Java Team
 */
public class Answer implements Serializable{
    private int id;
    private Object result;

    public Answer() {
    }

    public Answer(int id, Object result) {
        this.id = id;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", result=" + result +
                '}';
    }
}
