package com.example.parewa.data.model;

public class DashboardModel {
    int profile,postImgage;
    String name,like,comment;

    public DashboardModel(int profile, int postImgage, String name, String like, String comment) {
        this.profile = profile;
        this.postImgage = postImgage;
        this.name = name;
        this.like = like;
        this.comment = comment;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public int getPostImgage() {
        return postImgage;
    }

    public void setPostImgage(int postImgage) {
        this.postImgage = postImgage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
