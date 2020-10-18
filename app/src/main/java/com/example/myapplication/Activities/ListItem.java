package com.example.myapplication.Activities;
//v_id,v_name,v_image,v_video_url,v_user,v_desc
public class ListItem {
    int v_id;
    String v_name;
    String v_image;
    String v_video_url;
    String v_user;
    String v_desc;

    public ListItem() {
    }

    public ListItem(int v_id, String v_name, String v_image, String v_video_url, String v_user, String v_desc) {
        this.v_id = v_id;
        this.v_name = v_name;
        this.v_image = v_image;
        this.v_video_url = v_video_url;
        this.v_user = v_user;
        this.v_desc = v_desc;
    }

    public int getV_id() {
        return v_id;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
    }

    public String getV_name() {
        return v_name;
    }

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public String getV_image() {
        return v_image;
    }

    public void setV_image(String v_image) {
        this.v_image = v_image;
    }

    public String getV_video_url() {
        return v_video_url;
    }

    public void setV_video_url(String v_video_url) {
        this.v_video_url = v_video_url;
    }

    public String getV_user() {
        return v_user;
    }

    public void setV_user(String v_user) {
        this.v_user = v_user;
    }

    public String getV_desc() {
        return v_desc;
    }

    public void setV_desc(String v_desc) {
        this.v_desc = v_desc;
    }
}
