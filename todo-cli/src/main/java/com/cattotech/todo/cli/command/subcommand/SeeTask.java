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
        // manage with == null
        
        if (priority != null) {
            // manage call of function to show priority task
            
            return;
        }
        
        if (status != null) {
            
            return;
        }
        
        if (filterBy != null) {
            
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
