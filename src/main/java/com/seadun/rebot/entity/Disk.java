package com.seadun.rebot.entity;

import java.util.Date;

public class Disk {
    private String id;

    private String computerId;

    private String diskSn;

    private String diskInterfaceType;

    private String diskCapacity;

    private String diskShellSn;

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

    public String getDiskSn() {
        return diskSn;
    }

    public void setDiskSn(String diskSn) {
        this.diskSn = diskSn;
    }

    public String getDiskInterfaceType() {
        return diskInterfaceType;
    }

    public void setDiskInterfaceType(String diskInterfaceType) {
        this.diskInterfaceType = diskInterfaceType;
    }

    public String getDiskCapacity() {
        return diskCapacity;
    }

    public void setDiskCapacity(String diskCapacity) {
        this.diskCapacity = diskCapacity;
    }

    public String getDiskShellSn() {
        return diskShellSn;
    }

    public void setDiskShellSn(String diskShellSn) {
        this.diskShellSn = diskShellSn;
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