package com.zhx.app.eventbusdemmp;

import android.util.Log;

/**
 * Created by Administrator on 2018/4/24.
 */

public class UserEvent {
    private String name;

    public int username=1;
    private String password;

    public UserEvent() {
    }

    public void getUser(){
        Log.d("_tag","userEvent");
    }

    public UserEvent(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
