package com.cattotech.todo.cli.model;

import com.cattotech.todo.cli.utils.Priority;
import com.cattotech.todo.cli.utils.Status;
import java.util.Date;


public class Task {

    private String name;
    private String description;
    private Priority priority;
    private Status status;
    private Date dateLimit;

    public Task(String name, String description, Priority priority, Status status, Date dateLimit) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dateLimit = dateLimit;
    }

    public Task(String name) {
        this.name = name;
        
        // default values
        this.description = "none";
        this.priority = Priority.LOW;
        this.status = Status.NOT_STARTED;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public Date getDateLimit() {
        return dateLimit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDateLimit(Date dateLimit) {
        this.dateLimit = dateLimit;
    }
    
    
    
    
}
