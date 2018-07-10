package com.seadun.rebot.entity;

import java.util.Date;

public class Contract {
    private String id;

    private String contract;

    private Date crtTime;

    private Date uptTime;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}