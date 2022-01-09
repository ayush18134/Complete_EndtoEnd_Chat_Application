package com.example.whatsup.models;

public class message {
    String uid, text;
    Long timestanp;

    public message(){}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTimestanp() {
        return timestanp;
    }

    public void setTimestanp(Long timestanp) {
        this.timestanp = timestanp;
    }

    public message(String uid, String text) {
        this.uid = uid;
        this.text = text;
    }

    public message(String uid, String text, Long timestanp) {
        this.uid = uid;
        this.text = text;
        this.timestanp = timestanp;
    }
}
