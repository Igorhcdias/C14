package com.igor.app;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        name = "task",
        version = "TaskCLI 1.0.0",
        mixinStandardHelpOptions = true,
        description = "Gerenciador de tarefas via terminal.",
        subcommands = {
                com.igor.app.commands.AddCommand.class,
                com.igor.app.commands.ListCommand.class,
                com.igor.app.commands.DoneCommand.class,
                com.igor.app.commands.RemoveCommand.class,
                com.igor.app.commands.StatsCommand.class
        }
)
public class Main implements Runnable {
    public static void main(String[] args) {
        System.exit(new CommandLine(new Main()).execute(args));
    }
    @Override public void run() {
        new CommandLine(this).usage(System.out); // mostra --help se rodar sem subcomandos
    }
}
