package com.seadun.rebot.entity;

import java.util.Date;

public class Video {
    private String id;

    private String computerId;

    private String videoSn;

    private String videoType;

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

    public String getVideoSn() {
        return videoSn;
    }

    public void setVideoSn(String videoSn) {
        this.videoSn = videoSn;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
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