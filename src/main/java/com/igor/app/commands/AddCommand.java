package com.igor.app.commands;

import com.igor.app.model.Todo;
import com.igor.app.storage.JsonStore;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Command(name = "add", description = "Adiciona uma tarefa. Ex.: task add \"estudar java\" --due 2025-09-10")
public class AddCommand implements Runnable {

    @Parameters(arity = "1..*", description = "Título da tarefa (pode ser várias palavras).")
    List<String> titleParts = new ArrayList<>();

    @Option(names = "--due", description = "Data de vencimento (YYYY-MM-DD).")
    String dueStr;

    @Override
    public void run() {
        String title = String.join(" ", titleParts).trim();
        if (title.isEmpty()) {
            System.err.println("Título não pode ser vazio.");
            return;
        }

        LocalDate due = null;
        if (dueStr != null && !dueStr.isBlank()) {
            try { due = LocalDate.parse(dueStr); }
            catch (Exception e) { System.err.println("Data inválida. Use o formato YYYY-MM-DD."); return; }
        }

        JsonStore store = new JsonStore();
        var todos = store.load();
        Todo todo = new Todo(title, due);
        todos.add(todo);
        store.save(todos);

        System.out.println("Adicionado: " + todo.getId() + " - " + todo);
    }
}
