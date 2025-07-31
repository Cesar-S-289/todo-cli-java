package com.cattotech.todo.cli.service;

import com.cattotech.todo.cli.repository.ITaskRepository;

public class TaskService implements ITaskService {
    
    private final ITaskRepository taskRepo;

    public TaskService(ITaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }
    
}
