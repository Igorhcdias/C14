package com.igor.app;

import com.igor.app.model.Todo;
import com.igor.app.storage.JsonStore;
import com.igor.app.test.TestBase;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonStoreTest extends TestBase {

    @Test
    void iniciaComArquivoVazioEAoLerRetornaListaVazia() {
        JsonStore s = new JsonStore();
        assertTrue(Files.exists(s.getFilePath()));
        assertTrue(s.load().isEmpty());
    }

    @Test
    void salvaERecuperaTarefasComDatas() {
        JsonStore s = new JsonStore();
        Todo a = new Todo("A", LocalDate.now().plusDays(1));
        Todo b = new Todo("B", null);
        s.save(List.of(a, b));

        var lidas = s.load();
        assertEquals(2, lidas.size());
        assertEquals("A", lidas.get(0).getTitle());
    }

    @Test
    void lidaComJsonCorrompidoRetornandoListaVazia() throws Exception {
        JsonStore s = new JsonStore();
        Files.writeString(s.getFilePath(), "!!!nao eh json!!!", StandardCharsets.UTF_8);
        assertTrue(s.load().isEmpty());
    }

    @Test
    void persisteMarcacaoDeConcluida() {
        JsonStore s = new JsonStore();
        Todo t = new Todo("X", null);
        s.save(List.of(t));
        var l = s.load();
        l.get(0).setDone(true);
        s.save(l);
        assertTrue(s.load().get(0).isDone());
    }

    @Test
    void usaDiretorioDoUserHomeTemporarioNosTestes() {
        JsonStore s = new JsonStore();
        assertTrue(s.getFilePath().toString().contains(".taskcli"));
    }
}
