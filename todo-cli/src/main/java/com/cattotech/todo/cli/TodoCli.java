package com.cattotech.todo.cli;

import com.cattotech.todo.cli.command.CattoCli;
import com.cattotech.todo.cli.command.subcommand.AddTask;
import picocli.CommandLine;

public class TodoCli {

    public static void main(String[] args) {
        
        // instanciate and inject dependencies from service and repositories
        
        //adding subcomand with his implementations
        
        CommandLine cmd = new CommandLine(new CattoCli());
        //cmd.addSubcommand(new AddTask()); 
        
        
        
        int exitCode = cmd.execute( "add", "text for test", "--quick"); // change to args later
        System.exit(exitCode);
    }
}
