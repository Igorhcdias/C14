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
        assertTrue(out.contains("Adicionado:"), "Deveria informar que adicionou");

        List<Todo> todos = new JsonStore().load();
        assertEquals(1, todos.size());
        assertEquals("Estudar Java", todos.get(0).getTitle());
        assertNotNull(todos.get(0).getDue());
    }

    @Test
    void dataInvalidaMostraMensagemDeErro() {
        String out = CLITestHelper.exec(new AddCommand(), "Teste", "--due", "2025-99-99");
        assertTrue(out.contains("Data inválida"), "Esperava mensagem de data inválida");
        assertTrue(new JsonStore().load().isEmpty(), "Não deve salvar nada quando a data é inválida");
    }

    @Test
    void semTituloDisparaErroDeParametroObrigatorio() {
        String out = CLITestHelper.exec(new AddCommand() /* sem argumentos */);
        String lower = out.toLowerCase();
        assertTrue(lower.contains("missing") || lower.contains("obrigatório") || lower.contains("required"),
                "Deveria indicar parâmetro obrigatório ausente");
    }
}
