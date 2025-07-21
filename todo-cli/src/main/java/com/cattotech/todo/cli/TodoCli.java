package com.cattotech.todo.cli;

import com.cattotech.todo.cli.command.CattoCli;
import picocli.CommandLine;

public class TodoCli {

    public static void main(String[] args) {
        
        int exitCode = new CommandLine(new CattoCli()).execute( "add", "text for test", "--quick");
        System.exit(exitCode);
    }
}
