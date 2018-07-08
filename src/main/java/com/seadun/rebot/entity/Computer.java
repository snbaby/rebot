package com.seadun.rebot.entity;

import java.util.Date;

public class Computer {
    private String id;

    private String biosSn;

    private String opSystem;

    private String opInstallDate;

    private String ip;

    private Date crtDate;

    private Date uptDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBiosSn() {
        return biosSn;
    }

    public void setBiosSn(String biosSn) {
        this.biosSn = biosSn;
    }

    public String getOpSystem() {
        return opSystem;
    }

    public void setOpSystem(String opSystem) {
        this.opSystem = opSystem;
    }

    public String getOpInstallDate() {
        return opInstallDate;
    }

    public void setOpInstallDate(String opInstallDate) {
        this.opInstallDate = opInstallDate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public Date getUptDate() {
        return uptDate;
    }

    public void setUptDate(Date uptDate) {
        this.uptDate = uptDate;
    }
}