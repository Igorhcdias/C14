package com.igor.app.commands;

import com.igor.app.storage.JsonStore;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;   // só no ListCommand


@Command(name = "done", description = "Marca uma tarefa como concluída. Ex.: task done <id>")
public class DoneCommand implements Runnable {

    @Parameters(arity = "1", description = "ID da tarefa")
    String id;

    @Override
    public void run() {
        JsonStore store = new JsonStore();
        var todos = store.load();
        boolean found = false;
        for (var t : todos) {
            if (t.getId().equals(id)) {
                t.setDone(true);
                found = true;
                break;
            }
        }
        if (!found) {
            System.err.println("ID não encontrado.");
            return;
        }
        store.save(todos);
        System.out.println("Tarefa marcada como concluída: " + id);
    }
}
