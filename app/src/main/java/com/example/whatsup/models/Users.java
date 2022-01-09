package com.example.whatsup.models;

public class Users {
    String profilephoto,username,mail,password,userid,lastmessage;

    public Users(String profilephoto, String username, String mail, String password, String userid, String lastmessage) {
        this.profilephoto = profilephoto;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.userid = userid;
        this.lastmessage = lastmessage;
    }
    public Users(){}
    public Users(String username, String mail, String password) {
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Users{ username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid(String key) {
        return userid;
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }
}
