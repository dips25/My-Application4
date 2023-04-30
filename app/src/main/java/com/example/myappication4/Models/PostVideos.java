package com.example.myappication4.Models;

public class PostVideos {

    String id;
    String name;
    String time;
    String date;
    String url;
    boolean isSelected;

    public PostVideos(String id , String name , String time , String date , boolean isSelected) {

        this.id = id;
        this.name = name;
        this.time = time;
        this.date = date;
        this.isSelected = isSelected;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
