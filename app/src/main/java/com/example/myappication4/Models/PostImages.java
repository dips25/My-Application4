package com.example.myappication4.Models;

import org.xml.sax.helpers.AttributesImpl;

import java.util.ArrayList;
import java.util.List;

public class PostImages {

    String id;
    String name;
    String time;
    String date;
    String url;
    String type;
    boolean isSelected;
    String description;
    String profileImage;
    List<String> posts = new ArrayList<>();
    List<Content> contents = new ArrayList<>();


    public PostImages() {


    }

    public PostImages(String id , String name , String time , String date , boolean isSelected , ArrayList<String> posts , ArrayList<Content> contents) {

        this.id = id;
        this.name = name;
        this.time = time;
        this.date = date;
        this.isSelected = isSelected;
        this.posts = posts;
        this.contents = contents;


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getPosts() {
        return posts;
    }

    public void setPosts(List<String> posts) {
        this.posts = posts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}
