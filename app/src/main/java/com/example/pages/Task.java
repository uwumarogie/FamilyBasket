package com.example.pages;

public class Task {
    private String item;
    private String date;

    private boolean status;


    public Task(String item, String date, boolean status) {
        this.item = item;
        this.date = date;
        this.status = status;
    }

    public String getTasks() {
        return item;
    }

    public void setTasks(String tasks) {
        this.item  = tasks;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
