package com.cattotech.todo.cli.model;

import com.cattotech.todo.cli.utils.Priority;
import com.cattotech.todo.cli.utils.Status;
import java.time.LocalDate;


public class Task {

    private Long id;
    private String name;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDate dateLimit = null;

    public Task(String name, String description, Priority priority, Status status, LocalDate date) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dateLimit = date;
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

    public LocalDate getDateLimit() {
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

    public void setDateLimit(LocalDate dateLimit) {
        this.dateLimit = dateLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
