package com.example.repitout;

public class User {
    public String user_Name;
    public String user_Phone;
    public String user_email;

    public User(){

    }

    public User(String user_Name, String user_Phone, String user_email) {
        this.user_Name = user_Name;
        this.user_Phone = user_Phone;
        this.user_email = user_email;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getUser_Phone() {
        return user_Phone;
    }

    public void setUser_Phone(String user_Phone) {
        this.user_Phone = user_Phone;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

}
