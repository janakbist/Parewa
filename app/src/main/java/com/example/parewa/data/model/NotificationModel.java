package com.example.parewa.data.model;

public class NotificationModel {
    int profile;
    String time,notification;

    public NotificationModel(int profile, String time, String notification) {
        this.profile = profile;
        this.time = time;
        this.notification = notification;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
