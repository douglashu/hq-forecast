package com.hq.flood.demo.redis;

import java.io.Serializable;

/**
 * Created by Zale on 16/10/9.
 */
public class Model implements Serializable{

    private static final long serialVersionUID = -5472237071364894792L;
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
