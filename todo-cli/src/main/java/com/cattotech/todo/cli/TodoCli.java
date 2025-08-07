package com.cattotech.todo.cli;

import com.cattotech.todo.cli.command.CattoCli;
import com.cattotech.todo.cli.command.subcommand.AddTask;
import com.cattotech.todo.cli.command.subcommand.SeeTask;
import com.cattotech.todo.cli.repository.TaskRepository;
import com.cattotech.todo.cli.service.TaskService;
import picocli.CommandLine;

public class TodoCli {

    public static void main(String[] args) {
        
        // instanciate and inject dependencies from service and repositories
        TaskRepository repo = new TaskRepository();
        TaskService serv = new TaskService(repo);
        
        // initialize the databse
        repo.initializeDataBase();
        
        //adding subcomand with his implementations
        CommandLine cmd = new CommandLine(new CattoCli());
        cmd.addSubcommand(new AddTask(serv));
        cmd.addSubcommand(new SeeTask(serv));
        
        
        
        int exitCode = cmd.execute( "add", "first"); // change to args later
        System.exit(exitCode);
    }
}
