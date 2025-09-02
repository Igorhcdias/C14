package com.igor.app.commands;

import com.igor.app.storage.JsonStore;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;   // só no ListCommand


@Command(name = "remove", aliases = {"rm", "del"}, description = "Remove uma tarefa. Ex.: task remove <id>")
public class RemoveCommand implements Runnable {

    @Parameters(arity = "1", description = "ID da tarefa")
    String id;

    @Override
    public void run() {
        JsonStore store = new JsonStore();
        List<com.igor.app.model.Todo> todos = store.load();
        boolean removed = todos.removeIf(t -> t.getId().equals(id));
        if (!removed) {
            System.err.println("ID não encontrado.");
            return;
        }
        store.save(todos);
        System.out.println("Tarefa removida: " + id);
    }
}
