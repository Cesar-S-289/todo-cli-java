package com.cattotech.todo.cli.service;

import com.cattotech.todo.cli.model.Task;
import java.util.ArrayList;

public interface ITaskService {
    
    //  -------------- FOR ADD -----------------------
    public void addTask(String name, String description, String status, String priority, String date);
    
    //  -------------- FOR SEE -----------------------
    
    // show all task    
    public ArrayList<Task> showAll();
    
    // check if exist
    public boolean existTask(String name);
    
    // show only X priority task 
    public ArrayList<Task> showForPriority(String priority);
    // show only X status task
    public ArrayList<Task> showForStatus(String status);
    
    // task ordered from high to low
    public ArrayList<Task> fromHighToLow();
    // task ordered from low to high
    public ArrayList<Task> fromLowToHigh();
    // task ordered from high to low
    public ArrayList<Task> fromDoneToNotStarted();
    // task ordered from high to low
    public ArrayList<Task> fromNotStartedToDone();    
        
    //  -------------- FOR UPDATE -----------------------
    
    // modify all fields of the task
    public void modifyAllTask(String name);
    // modify only one field
    public void modifyOne(String name, String field);
    
    
    //  -------------- FOR DELETE -----------------------
    
    public void deleteOne(String name);
    
    public void deleteAll();

}
