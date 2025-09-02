package com.igor.app.commands;

import com.igor.app.model.Todo;
import com.igor.app.storage.JsonStore;

import picocli.CommandLine.Command;

import java.time.LocalDate;
import java.util.List;



@Command(name = "stats", description = "Mostra estatísticas das tarefas")
public class StatsCommand implements Runnable {
    @Override
    public void run() {
        JsonStore store = new JsonStore();
        List<Todo> todos = store.load();
        long total = todos.size();
        long feitas = todos.stream().filter(Todo::isDone).count();
        long pendentes = total - feitas;
        long atrasadas = todos.stream()
                .filter(t -> !t.isDone() && t.getDue() != null && t.getDue().isBefore(LocalDate.now()))
                .count();

        System.out.println("=== Estatísticas ===");
        System.out.println("Total:      " + total);
        System.out.println("Concluídas: " + feitas);
        System.out.println("Pendentes:  " + pendentes);
        System.out.println("Atrasadas:  " + atrasadas);
    }
}
