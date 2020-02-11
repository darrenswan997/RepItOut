package com.example.repitout;

public class Workout {

    private String title;
    private String link;

    public Workout(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public Workout() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
