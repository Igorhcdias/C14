package com.igor.app.commands;

import com.igor.app.model.Todo;
import com.igor.app.storage.JsonStore;
import com.igor.app.test.CLITestHelper;
import com.igor.app.test.TestBase;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddCommandTest extends TestBase {

    @Test
    void adicionaComSucesso() {
        String out = CLITestHelper.exec(new AddCommand(),
                "Estudar", "Java", "--due", LocalDate.now().plusDays(2).toString());
        assertTrue(out.contains("Adicionado:"));

        List<Todo> todos = new JsonStore().load();
        assertEquals(1, todos.size());
        assertEquals("Estudar Java", todos.get(0).getTitle());
        assertNotNull(todos.get(0).getDue());
    }

    @Test
    void dataInvalidaMostraMensagemDeErro() {
        String out = CLITestHelper.exec(new AddCommand(), "Teste", "--due", "2025-99-99");
        assertTrue(out.contains("Data inválida"));
        assertTrue(new JsonStore().load().isEmpty());
    }

    @Test
    void semTituloDisparaErroDeParametroObrigatorio() {
        String out = CLITestHelper.exec(new AddCommand());
        String lower = out.toLowerCase();
        assertTrue(lower.contains("missing") || lower.contains("obrigatório") || lower.contains("required"));
    }
}
