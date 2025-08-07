package com.cattotech.todo.cli.service;

import com.cattotech.todo.cli.model.Task;
import com.cattotech.todo.cli.repository.ITaskRepository;
import com.cattotech.todo.cli.utils.Priority;
import com.cattotech.todo.cli.utils.Status;
import com.cattotech.todo.cli.utils.Utils;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskService implements ITaskService {
    
    private final ITaskRepository taskRepo;

    public TaskService(ITaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    // somenthing is wrong with status, check later
    
    @Override
    public void addTask(String name, String description, String status, String priority, String date) {
        
        // if somenthing here is null, was quick task command
        if (description == null || status == null) {
            Task taskToSave = new Task(name);
            taskRepo.insertTask(taskToSave);
            return;
        }
        
        Priority pr = Priority.valueOf(priority.toUpperCase());
        
        Status st;
        if (status.equalsIgnoreCase("on going")){
            st = Status.ON_GOING;
        } else {
            st = Status.NOT_STARTED;
        }
        
        LocalDate dt = Utils.getDate(date);
        
        Task taskToSave = new Task(name, description, pr, st, dt);
        taskRepo.insertTask(taskToSave);
    }

    @Override
    public boolean existTask(String name) {
        Task checkTask =  taskRepo.getTaskByName(name);
        
        return checkTask != null;
    }
    
    @Override
    public ArrayList<Task> showAll() {
        // manage null, like empty list
        ArrayList<Task> response = taskRepo.getAllTasks();
        
        if (response == null){
            System.out.println("No tasks founds, try adding one.");
            return null;
        }
        
        return response;
    }

    @Override
    public ArrayList<Task> showOnlyHigh(String priority) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Task> showOnlyDone(String priority) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Task> fromHighToLow() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Task> fromLowToHigh() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Task> fromDoneToNotStarted() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Task> fromNotStartedToDone() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void modifyAllTask(Long id, String name, String description, String priority, String Status, String date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void modifyOne(String name, String field, String newValue) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteOne(String name) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
