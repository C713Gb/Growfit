package com.example.myapplication.Models;

public class Task {

    String name;
    String done;
    String date;

    public Task(String name, String done, String date) {
        this.name = name;
        this.done = done;
        this.date = date;
    }

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
