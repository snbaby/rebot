package com.seadun.rebot.entity;

import java.util.Date;

public class User {
    private String id;

    private String userName;

    private String userPassword;

    private Date crtTime;

    private Date crtUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Date getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(Date crtUser) {
        this.crtUser = crtUser;
    }
}