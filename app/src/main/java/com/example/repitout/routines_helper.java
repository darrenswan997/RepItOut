package com.example.repitout;

import java.util.ArrayList;
import java.util.Map;

public class routines_helper {

    private Map<String, String> date;
    private String name, day, month, year;
    private ArrayList<String> excList;

    public routines_helper(String name, String day, String month, String year) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;

    }



    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public routines_helper(String name, Map<String, String> date) {
        this.name = name;
        this.date = date;
    }


    public Map<String, String> getDate() {
        return date;
    }

    public void setDate(Map<String, String> date) {
        this.date = date;
    }


}
