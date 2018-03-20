package cn.dubby.blog.entity;

import java.io.Serializable;

/**
 * Created by teeyoung on 17/12/12.
 */
public class Configuration implements Serializable {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
