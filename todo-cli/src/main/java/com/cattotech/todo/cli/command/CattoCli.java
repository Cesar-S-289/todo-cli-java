package com.cattotech.todo.cli.command;

import com.cattotech.todo.cli.command.subcommand.AddTask;
import com.cattotech.todo.cli.command.subcommand.DeleteTask;
import com.cattotech.todo.cli.command.subcommand.SeeTask;
import com.cattotech.todo.cli.command.subcommand.UpdateTask;
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
        // ejecutamos la logica con los parametros y opciones que pedimos y obtenimos
    }
    
}
