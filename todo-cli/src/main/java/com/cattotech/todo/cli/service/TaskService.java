package com.cattotech.todo.cli.service;

import com.cattotech.todo.cli.model.Task;
import com.cattotech.todo.cli.repository.ITaskRepository;
import com.cattotech.todo.cli.utils.Priority;
import com.cattotech.todo.cli.utils.Status;
import com.cattotech.todo.cli.utils.Utils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskService implements ITaskService {

    private final ITaskRepository taskRepo;
    private final String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";

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
    public void modifyAllTask(String name) {
        // manage all input of data
        // if we recive one "-", that field keep his value
        Task taskToUpdate = taskRepo.getTaskByName(name);
        System.out.println("We request the value for each field, if you don't want to change one, enter '-' ");
        
        try (Scanner scn = new Scanner(System.in)) {
            String newName, newDate, newStatus, newDescription, newPriority;
            
            // do while
            do {
                System.out.println("Insert new name (if name is used, we ask again for it): ");
                // i need to add some message from repeated name
                newName = scn.nextLine();
            } while (this.existTask(newName));
            
            // check if user want to change the value
            if (!newName.equalsIgnoreCase("-")) {
                taskToUpdate.setName(newName);
            }
            
            System.out.println("\ninsert new description: ");
            // get description and check if we need to update it
            newDescription = scn.nextLine();
            if(!newDescription.equalsIgnoreCase("-")){
                taskToUpdate.setDescription(newDescription);
            }
            
            // getting and set priority
            do {
                System.out.println("\nselect priority (low, medium, high): ");
                newPriority = scn.nextLine();
            } while (!(newPriority.equalsIgnoreCase("-") || newPriority.equalsIgnoreCase("low") || newPriority.equalsIgnoreCase("medium") || newPriority.equalsIgnoreCase("high")));
            if(newPriority.equalsIgnoreCase("-")) {
                // update the value of priority with valueOf
                taskToUpdate.setPriority(Priority.valueOf(newPriority.toUpperCase()));
            }
            
            // getting and seting status
            do {
                System.out.println("\nselect stauts (not started, on going or done): ");
                newStatus = scn.nextLine();
            } while (!(newStatus.equalsIgnoreCase("-") || newStatus.equalsIgnoreCase("not started") || newStatus.equalsIgnoreCase("on going") || newStatus.equalsIgnoreCase("done")));
            
            if (!newStatus.equalsIgnoreCase("-")) {
                switch (newStatus) {
                    case "on going" ->
                        taskToUpdate.setStatus(Status.ON_GOING);
                    case "not started" ->
                        taskToUpdate.setStatus(Status.NOT_STARTED);
                    case "done" ->
                       taskToUpdate.setStatus(Status.DONE);
                }
            }
            
            
            do {
                System.out.println("\ninsert limit date(dd/MM/yyyy):");
                newDate = scn.next();
            } while (!(newDate.matches(regex) || newDate.equalsIgnoreCase("-")));
            
            if (!newDate.equalsIgnoreCase("-")) {
                taskToUpdate.setDateLimit(Utils.getDate(newDate));
            }
            
            // pass the data and update it in repo
            
            taskRepo.updateTask(taskToUpdate);
        } 
        
    }

    @Override
    public void modifyOne(String name, String field) {
        // i need to get the new value with the scaner
        Task taskToUpdate = taskRepo.getTaskByName(name);
        String value;
        
        try (Scanner scn = new Scanner(System.in)) {
            switch (field.toLowerCase()) {
                case "name" -> {
                    // repeat until valid value with do while
                    do {
                        System.out.println("give a new name for the task (must not be in use):");
                        value = scn.nextLine();
                    } while (this.existTask(value));

                    taskToUpdate.setName(value);
                }
                case "description" -> {
                    System.out.println("Enter a new description (couldn´t be empty):");
                    value = scn.nextLine();
                    // not allow void values
                    if (value.length() == 0) {
                        System.out.println("finished without update");
                        return;
                    }
                    
                    taskToUpdate.setDescription(value);
                }

                case "date" -> {
                    do {
                        System.out.println("Enter a date with the next pattern dd/MM/yyyy:");
                        value = scn.nextLine();
                    } while (!value.matches(regex));

                    LocalDate newDate = Utils.getDate(value);
                    taskToUpdate.setDateLimit(newDate);
                }

                case "status" -> {
                    do {
                        System.out.println("\nselect stauts (not started, on going or done): ");
                        value = scn.nextLine();
                    } while (!( value.equalsIgnoreCase("not started") || value.equalsIgnoreCase("on going") || value.equalsIgnoreCase("done")));
                    
                    
                    switch (value) {
                        case "on going" -> taskToUpdate.setStatus(Status.ON_GOING);
                        case "not started" -> taskToUpdate.setStatus(Status.NOT_STARTED);
                        case "done" -> taskToUpdate.setStatus(Status.DONE);
                    }
                }
                
                case "priority" -> {
                    do {
                        System.out.println("\nselect priority (low, medium, high): ");
                        value = scn.nextLine();
                    } while (!(value.equalsIgnoreCase("low") || value.equalsIgnoreCase("medium") || value.equalsIgnoreCase("high")));
                        // update the value of priority with valueOf
                        taskToUpdate.setPriority(Priority.valueOf(value.toUpperCase()));
                }
                    
                default -> {
                    System.out.println("Invalid field provided");
                    return;
                }
            }
        }

        taskRepo.updateTask(taskToUpdate);
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
