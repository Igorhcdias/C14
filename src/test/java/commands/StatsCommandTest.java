package com.igor.app.commands;

import com.igor.app.model.Todo;
import com.igor.app.storage.JsonStore;
import com.igor.app.test.CLITestHelper;
import com.igor.app.test.TestBase;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatsCommandTest extends TestBase {

    @Test
    void mostraEstatisticasComDados() {
        Todo a = new Todo("A", LocalDate.now().minusDays(1)); // atrasada
        Todo b = new Todo("B", null); b.setDone(true);
        new JsonStore().save(List.of(a, b));

        String out = CLITestHelper.exec(new StatsCommand());
        assertTrue(out.contains("Total:      2"));
        assertTrue(out.contains("Concluídas: 1"));
        assertTrue(out.contains("Pendentes:  1"));
        assertTrue(out.contains("Atrasadas:  1"));
    }

    @Test
    void estatisticasZeradasQuandoNaoHaTarefas() {
        String out = CLITestHelper.exec(new StatsCommand());
        assertTrue(out.contains("Total:      0"));
    }
}
