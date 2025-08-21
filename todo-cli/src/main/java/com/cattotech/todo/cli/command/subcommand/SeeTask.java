package com.cattotech.todo.cli.command.subcommand;

import com.cattotech.todo.cli.model.Task;
import com.cattotech.todo.cli.service.ITaskService;
import java.util.ArrayList;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "see",
        mixinStandardHelpOptions = true,
        description = "subcommand to see your tasks.")
public class SeeTask implements Runnable{
    
    private final ITaskService taskServ;
    
    @Option(names = {"-p", "--priority"}, description = "todo")
    String priority;
    
    @Option(names = {"-s", "--status"}, description = "todo")
    String status;
    
    @Option(names = {"-f", "--filter"}, description = "todo")
    String filterBy;

    @Override
    public void run() {
        
        if (priority != null) {
            // manage call of function to show priority task
            if ( !(priority.equalsIgnoreCase("low") || priority.equalsIgnoreCase("high") || priority.equalsIgnoreCase("medium"))) {
                System.out.println("invalid priority, insert again");
            }
            
            ArrayList <Task> response = taskServ.showForPriority(priority);
            // checking if doesn't exist tasks with that priority
            if (response == null) {
                System.out.println("No " + priority + " priority tasks were found.");
                return;
            }
            
            // only for test, implement printing later
            for (Task rs : response) {
                System.out.println("name: " + rs.getName() + " priority: " + rs.getPriority().toString());
            }
            
            // this return is because the command aren't mean to work together
            return;
        }
        
        if (status != null) {
            
            if (! (status.equalsIgnoreCase("on going") || status.equalsIgnoreCase("not started") || status.equalsIgnoreCase("done"))) {
                System.out.println("invalid status, insert again");
                return;
            }
            ArrayList<Task> response = taskServ.showForStatus(status);
            // checking if doesn't exist tasks with that status
            if (response == null) {
                System.out.println("No " + status + " status task were found.");
                return;
            }
            
            // only for test
            for (Task rs : response) {
                System.out.println("name: " + rs.getName() + " status: " + rs.getStatus().toString());
            }
            
            // this return is because the command aren't mean to work together
            return;
        }
        
        if (filterBy != null) {
            
            if (filterBy.equalsIgnoreCase("done")) {
                ArrayList<Task> response = taskServ.fromDoneToNotStarted();
                
                if (response != null) {

                    for (Task rs : response) {
                        System.out.println("name: " + rs.getName() + " status: " + rs.getStatus().toString());
                    }
                    
                    return;
                }
            }
            
            if (filterBy.equalsIgnoreCase("not started")) {
                ArrayList<Task> response = taskServ.fromNotStartedToDone();
                
                if (response != null) {
                    for (Task rs : response) {
                        System.out.println("name: " + rs.getName() + " status: " + rs.getStatus().toString());
                    }
                    
                    return;
                }
            }
            
            if (filterBy.equalsIgnoreCase("low")) {
                ArrayList<Task> response = taskServ.fromLowToHigh();
                
                if (response != null) {
                    for (Task rs : response) {
                        System.out.println("name: " + rs.getName() + " priority: " + rs.getPriority().toString());
                    }
                    
                    return;
                }
            }
            
            if (filterBy.equalsIgnoreCase("high")) {
                ArrayList<Task> response = taskServ.fromHighToLow();
                if (response != null) {
                    for (Task rs : response) {
                        System.out.println("name: " + rs.getName() + " priority: " + rs.getPriority().toString());
                    }
                    
                    return;
                }
            }
            
            System.out.println("Seems like you don't have task loaded");
            return;
        }
        
        // if no other command would be executed, we need to show all task
        
        ArrayList<Task> tasks = taskServ.showAll();
        
        if (tasks != null) {
            // convert this into streams later
            for (Task ts : tasks) {
                System.out.println(ts.getName());
            }
        }
    }

    public SeeTask(ITaskService taskServ) {
        this.taskServ = taskServ;
    }
    
}
