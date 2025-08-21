
package com.cattotech.todo.cli.command.subcommand;

import com.cattotech.todo.cli.service.ITaskService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "update", mixinStandardHelpOptions = true, description = "Command for update tasks")
public class UpdateTask implements Runnable {
    
    private final ITaskService taskServ;

    @Parameters(index = "0", description = "name of task would be updated")
    String taskName;
    
    @Option(names = {"-f, --field"}, description = "field to update in quick option")
    String field;
    
    @Option(names = {"-o", "--oneField"}, description = "enable option for update only one field")
    boolean oneField = false;
    
    
    @Override
    public void run() {
        
        // update all the task
        if (oneField == false) {
            taskServ.modifyAllTask(taskName);
            return;
        }
        
        // avoid call
        if (field == null) {
            System.out.println("You must provide a field to update");
            return;
        }
        
        // workflow for update one
        taskServ.modifyOne(taskName, field);
    }

    public UpdateTask(ITaskService taskServ) {
        this.taskServ = taskServ;
    }
    
    
}
