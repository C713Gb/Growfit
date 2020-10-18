package com.example.myapplication.Models;

public class Task {

    String name;
    String done;
    String date;
    String id;
    String dueDate;

    public Task(String name, String done, String date, String id, String dueDate) {
        this.name = name;
        this.done = done;
        this.date = date;
        this.id = id;
        this.dueDate = dueDate;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
