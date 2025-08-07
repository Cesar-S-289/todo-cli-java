package com.cattotech.todo.cli.command.subcommand;

import com.cattotech.todo.cli.service.ITaskService;
import java.util.Scanner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "add",
        mixinStandardHelpOptions = true,
        description = "subcommand to add a task.")
public class AddTask implements Runnable {
    
    private final ITaskService taskServ;

    public AddTask(ITaskService taskServ) {
        this.taskServ = taskServ;
    }

    @Parameters(index = "0", description = "task name")
    private String taskName;

    @Option(names = {"-q", "--quick"}, description = "save the task with only name, and set default values in others categories")
    boolean quickTask;

    @Override
    public void run() {
        
        // check if task name is used
        boolean exist = taskServ.existTask(taskName);
            if (exist) {
                System.out.println("That task already exists. use other name");
                return;
            }
            
        // TODO:
        // print exit message only if is needed 

        if (quickTask) {
            taskServ.addTask(taskName, null, null, null, null);
            System.out.println("task added succesfully");
            
            return;
        }

        String priority, status, date;
        // regex to check the date input
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";

        String description;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("add a description (max X char): ");
            description = scanner.nextLine();

            do {
                System.out.println("\nselect priority (low, medium, high): ");
                priority = scanner.nextLine();
            } while (!(priority.equalsIgnoreCase("low") || priority.equalsIgnoreCase("medium") || priority.equalsIgnoreCase("high")));

            do {
                System.out.println("\nselect stauts (not started, on going): ");
                status = scanner.nextLine();
            } while (!(status.equalsIgnoreCase("not started") || status.equalsIgnoreCase("on going")));

            do {
                System.out.println("\ninsert limit date(dd/MM/yyyy):");
                date = scanner.next();
            } while (!date.matches(regex));
        }
        
        taskServ.addTask(taskName, description, status, priority, date);
        
        // check if we need message later
    }

}
