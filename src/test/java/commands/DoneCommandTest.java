package com.igor.app.commands;

import com.igor.app.model.Todo;
import com.igor.app.storage.JsonStore;
import com.igor.app.test.CLITestHelper;
import com.igor.app.test.TestBase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoneCommandTest extends TestBase {

    @Test
    void marcaComoConcluida() {
        JsonStore s = new JsonStore();
        Todo t = new Todo("X", null);
        s.save(java.util.List.of(t));

        String out = CLITestHelper.exec(new DoneCommand(), t.getId());
        assertTrue(out.contains("Tarefa marcada como concluída"));

        assertTrue(new JsonStore().load().get(0).isDone());
    }

    @Test
    void idInvalidoMostraErro() {
        String out = CLITestHelper.exec(new DoneCommand(), "id-invalido");
        assertTrue(out.contains("ID não encontrado"));
    }
}
