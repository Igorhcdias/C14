package com.igor.app.commands;

import com.igor.app.model.Todo;
import com.igor.app.storage.JsonStore;
import com.igor.app.test.CLITestHelper;
import com.igor.app.test.TestBase;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListCommandTest extends TestBase {

    private void popula(JsonStore s) {
        List<Todo> list = new ArrayList<>();
        Todo pend = new Todo("pendente", null);
        Todo done = new Todo("feita", null); done.setDone(true);
        Todo atrasada = new Todo("atrasada", LocalDate.now().minusDays(1));
        list.add(pend); list.add(done); list.add(atrasada);
        s.save(list);
    }

    @Test void listaPendentes() {
        JsonStore s = new JsonStore(); popula(s);
        String out = CLITestHelper.exec(new ListCommand(), "--pending");
        assertTrue(out.contains("pendente"));
        assertFalse(out.contains("feita"));
    }

    @Test void listaConcluidas() {
        JsonStore s = new JsonStore(); popula(s);
        String out = CLITestHelper.exec(new ListCommand(), "--done");
        assertTrue(out.contains("feita"));
    }

    @Test void listaAtrasadas() {
        JsonStore s = new JsonStore(); popula(s);
        String out = CLITestHelper.exec(new ListCommand(), "--overdue");
        assertTrue(out.contains("atrasada"));
    }

    @Test void mensagemQuandoNaoHaTarefas() {
        String out = CLITestHelper.exec(new ListCommand());
        assertTrue(out.contains("Nenhuma tarefa encontrada."));
    }
}
