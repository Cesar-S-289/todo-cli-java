package com.cattotech.todo.cli.command;

import picocli.CommandLine.Command;


// nombre de momento task, para mezclarlo con el alias catto al ejecutable
@Command(name = "task",
        mixinStandardHelpOptions = true,
        description = "ToDo CLI application",
        requiredOptionMarker = '*'
)
public class CattoCli implements Runnable {
    
    @Override
    public void run() {
    }
    
}
