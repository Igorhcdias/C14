package com.igor.app.commands;

import com.igor.app.model.Todo;
import com.igor.app.storage.JsonStore;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;



@Command(name = "list", description = "Lista tarefas (filtros: --done, --pending, --overdue)")
public class ListCommand implements Runnable {

    @Option(names = "--done", description = "Mostra apenas concluídas")
    boolean onlyDone;

    @Option(names = "--pending", description = "Mostra apenas pendentes")
    boolean onlyPending;

    @Option(names = "--overdue", description = "Mostra apenas atrasadas")
    boolean onlyOverdue;

    @Override
    public void run() {
        JsonStore store = new JsonStore();
        List<Todo> todos = store.load();
        LocalDate today = LocalDate.now();

        Stream<Todo> stream = todos.stream();
        if (onlyDone) stream = stream.filter(Todo::isDone);
        if (onlyPending) stream = stream.filter(t -> !t.isDone());
        if (onlyOverdue) stream = stream.filter(t -> !t.isDone() && t.getDue() != null && t.getDue().isBefore(today));

        List<Todo> list = stream
                .sorted(Comparator
                        .comparing(Todo::isDone)
                        .thenComparing(t -> t.getDue() == null ? LocalDate.MAX : t.getDue()))
                .toList();

        if (list.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }

        System.out.printf("%-36s  %-6s  %-12s  %s%n", "ID", "FEITA", "VENCIMENTO", "TÍTULO");
        System.out.println("-".repeat(36) + "  " + "-".repeat(6) + "  " + "-".repeat(12) + "  " + "-".repeat(40));
        for (Todo t : list) {
            String due = t.getDue() == null ? "-" : t.getDue().toString();
            System.out.printf("%-36s  %-6s  %-12s  %s%n",
                    t.getId(), t.isDone() ? "sim" : "não", due, t.getTitle());
        }
        System.out.println("\nArmazenado em: " + store.getFilePath());
    }
}
