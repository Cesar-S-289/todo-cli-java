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
        if (status.equalsIgnoreCase("on going")) {
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
        Task checkTask = taskRepo.getTaskByName(name);

        return checkTask != null;
    }

    @Override
    public ArrayList<Task> showAll() {
        // manage null, like empty list
        ArrayList<Task> response = taskRepo.getAllTasks();

        if (response == null) {
            System.out.println("No tasks founds, try adding one.");
            return null;
        }

        return response;
    }

    @Override
    public ArrayList<Task> showForPriority(String priority) {
        // getting value from input and return
        return taskRepo.getTasksByPriority(priority.toUpperCase());
    }

    @Override
    public ArrayList<Task> showForStatus(String status) {
        String st;
        switch (status) {
            case "on going" ->
                st = "ON_GOING";
            case "not started" ->
                st = "NOT_STARTED";
            case "done" ->
                st = "DONE";
            default ->
                st = "NONE";
        }

        return taskRepo.getTasksByStatus(st);
    }

    @Override
    public ArrayList<Task> fromHighToLow() {

        ArrayList<Task> highTasks = this.showForPriority("high");
        ArrayList<Task> mediumTasks = this.showForPriority("medium");
        ArrayList<Task> lowTasks = this.showForPriority("low");

        // DB empty, no tasks in any arraylist
        if (highTasks == null && mediumTasks == null && lowTasks == null) {
            return null;
        }

        // with making new arraylist we avoid null pointerException and excesive logic from combination of lists
        // no high tasks in DB
        if (highTasks == null) {
            highTasks = new ArrayList<>();
        }
        // no medium tasks in DB
        if (mediumTasks == null) {
            mediumTasks = new ArrayList<>();
        }

        // no low tasks in DB
        if (lowTasks == null) {
            lowTasks = new ArrayList<>();
        }

        // normal case, all priority has tasks
        highTasks.addAll(mediumTasks);
        highTasks.addAll(lowTasks);

        return highTasks;
    }

    @Override
    public ArrayList<Task> fromLowToHigh() {
        ArrayList<Task> highTasks = this.showForPriority("high");
        ArrayList<Task> mediumTasks = this.showForPriority("medium");
        ArrayList<Task> lowTasks = this.showForPriority("low");

        // DB empty, no tasks in any arraylist
        if (highTasks == null && mediumTasks == null && lowTasks == null) {
            return null;
        }

        // with making new arraylist we avoid null pointerException and excesive logic from combination of lists
        // no high tasks in DB
        if (highTasks == null) {
            highTasks = new ArrayList<>();
        }
        // no medium tasks in DB
        if (mediumTasks == null) {
            mediumTasks = new ArrayList<>();
        }

        // no low tasks in DB
        if (lowTasks == null) {
            lowTasks = new ArrayList<>();
        }

        // normal case, all priority has tasks
        lowTasks.addAll(mediumTasks);
        lowTasks.addAll(highTasks);
        return lowTasks;
        
    }

    @Override
    public ArrayList<Task> fromDoneToNotStarted() {
        ArrayList<Task> doneTasks = this.showForStatus("done");
        ArrayList<Task> onGoingTasks = this.showForStatus("on going");
        ArrayList<Task> notStartedTasks = this.showForStatus("not started");

        // DB empty, no tasks in any arraylist
        if (doneTasks == null && onGoingTasks == null && notStartedTasks == null) {
            return null;
        }

        // with making new arraylist we avoid null pointerException and excesive logic from combination of lists
        // no done tasks in DB
        if (doneTasks == null) {
            doneTasks = new ArrayList<>();
        }
        // no on going tasks in DB
        if (onGoingTasks == null) {
            onGoingTasks = new ArrayList<>();
        }

        // no not started tasks in DB
        if (notStartedTasks == null) {
            notStartedTasks = new ArrayList<>();
        }

        // normal case, all priority has tasks
        doneTasks.addAll(onGoingTasks);
        doneTasks.addAll(notStartedTasks);

        return doneTasks;
    }

    @Override
    public ArrayList<Task> fromNotStartedToDone() {
        
        ArrayList<Task> doneTasks = this.showForStatus("done");
        ArrayList<Task> onGoingTasks = this.showForStatus("on going");
        ArrayList<Task> notStartedTasks = this.showForStatus("not started");

        // DB empty, no tasks in any arraylist
        if (doneTasks == null && onGoingTasks == null && notStartedTasks == null) {
            return null;
        }

        // with making new arraylist we avoid null pointerException and excesive logic from combination of lists
        // no done tasks in DB
        if (doneTasks == null) {
            doneTasks = new ArrayList<>();
        }
        // no on going tasks in DB
        if (onGoingTasks == null) {
            onGoingTasks = new ArrayList<>();
        }

        // no not started tasks in DB
        if (notStartedTasks == null) {
            notStartedTasks = new ArrayList<>();
        }

        // normal case, all priority has tasks
        notStartedTasks.addAll(onGoingTasks);
        notStartedTasks.addAll(doneTasks);

        return notStartedTasks;
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
