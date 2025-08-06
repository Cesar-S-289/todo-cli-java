package com.cattotech.todo.cli.repository;

import com.cattotech.todo.cli.model.Task;
import java.util.ArrayList;


public interface ITaskRepository {
    
    // initializacion of database
    public void initializeDataBase();
    
    // get one task by name
    public Task getTaskByName(String name);
    
    // get one task by id
    public Task getTaskById(Long id);
    
    // get all task 
    public ArrayList<Task> getAllTasks();
    
    // delete by name
    public void deleteByName();
    
    // delete All
    public void deleteAll();
    
    // insert task
    public void insertTask(Task task);
    
    // update task 
    public void updateTask(Long id);
    
   
}
