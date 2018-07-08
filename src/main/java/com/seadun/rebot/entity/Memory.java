package com.seadun.rebot.entity;

import java.util.Date;

public class Memory {
    private String id;

    private String computerId;

    private String memSn;

    private String memType;

    private String memCapacity;

    private Date crtTime;

    private Date uptTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComputerId() {
        return computerId;
    }

    public void setComputerId(String computerId) {
        this.computerId = computerId;
    }

    public String getMemSn() {
        return memSn;
    }

    public void setMemSn(String memSn) {
        this.memSn = memSn;
    }

    public String getMemType() {
        return memType;
    }

    public void setMemType(String memType) {
        this.memType = memType;
    }

    public String getMemCapacity() {
        return memCapacity;
    }

    public void setMemCapacity(String memCapacity) {
        this.memCapacity = memCapacity;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Date getUptTime() {
        return uptTime;
    }

    public void setUptTime(Date uptTime) {
        this.uptTime = uptTime;
    }
}