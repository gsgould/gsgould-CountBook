/*
        Object class Timer.java. This is the class that outlines the composition of a Timer Object.
        Timers take in an id, name, initial_count, current_count, comments and date. They have getter
        and setter methods as well as a basic toString() method to output some of the primary data.
 */
package ca.ghvideos.counter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Timer {
    int id;
    String name;
    int initial_count;
    int current_count;
    String comments;
    Date Date;

    public Timer() {
        //Empty Constructor

    }

    // All parameter constructor
    public Timer(int id, String name, int initial_count, int current_count, String comments) {
        this.id = id;
        this.name = name;
        this.initial_count = initial_count;
        this.current_count = current_count;
        this.comments = comments;
    }

    // 4 parameter constructor
    public Timer(int id, String name, int initial_count, int current_count) {
        this.id = id;
        this.name = name;
        this.initial_count = initial_count;
        this.current_count = current_count;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yy");
        String dateString;
        if (this != null) {
            dateString = sdf.format(this.getDate());
        } else {
            dateString = sdf.format(DateFormat.getDateInstance());
        }


        return this.getName() + ": Count(" + this.getCurrent_count() + ") Date(" + dateString + ")";
    }


    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInitial_count() {
        return initial_count;
    }

    public void setInitial_count(int initial_count) {
        this.initial_count = initial_count;
    }

    public int getCurrent_count() {
        return current_count;
    }

    public void setCurrent_count(int current_count) {
        this.current_count = current_count;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }
}