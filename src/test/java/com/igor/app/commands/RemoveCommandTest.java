package com.igor.app.commands;

import com.igor.app.model.Todo;
import com.igor.app.storage.JsonStore;
import com.igor.app.test.CLITestHelper;
import com.igor.app.test.TestBase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveCommandTest extends TestBase {

    @Test
    void removeComSucesso() {
        JsonStore s = new JsonStore();
        Todo t = new Todo("X", null);
        s.save(java.util.List.of(t));

        String out = CLITestHelper.exec(new RemoveCommand(), t.getId());
        assertTrue(out.contains("Tarefa removida"));

        assertTrue(new JsonStore().load().isEmpty());
    }

    @Test
    void idNaoEncontradoMostraErro() {
        String out = CLITestHelper.exec(new RemoveCommand(), "zzz");
        assertTrue(out.contains("ID não encontrado"));
    }
}
