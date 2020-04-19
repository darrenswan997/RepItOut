package com.example.repitout;


public class User_Profile_Details {

   public String user_age, user_height, user_weight;

    public User_Profile_Details(String user_age, String user_height, String user_weight) {
        this.user_age = user_age;
        this.user_height = user_height;
        this.user_weight = user_weight;
    }

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }

    public String getUser_height() {
        return user_height;
    }

    public void setUser_height(String user_height) {
        this.user_height = user_height;
    }

    public String getUser_weight() {
        return user_weight;
    }

    public void setUser_weight(String user_weight) {
        this.user_weight = user_weight;
    }


}
