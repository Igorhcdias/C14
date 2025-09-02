import com.igor.app.model.Todo;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class TodoTest {

    @Test
    void criaTodoComCamposBasicos() {
        LocalDate due = LocalDate.now().plusDays(3);
        Todo t = new Todo("Estudar Java", due);
        assertEquals("Estudar Java", t.getTitle());
        assertEquals(due, t.getDue());
        assertFalse(t.isDone());
        assertNotNull(t.getId());
        assertNotNull(t.getCreatedAt());
    }

    @Test
    void toStringIncluiDueQuandoInformado() {
        Todo t = new Todo("X", LocalDate.of(2030, 1, 1));
        String s = t.toString();
        assertTrue(s.contains("venc: 2030-01-01"));
        assertTrue(s.startsWith("[  ]"));
    }

    @Test
    void toStringSemDueQuandoNulo() {
        Todo t = new Todo("Sem data", null);
        assertFalse(t.toString().contains("venc:"));
    }

    @Test
    void equalsEHashCodeBaseadosNoId() {
        Todo a = new Todo("A", null);
        Todo b = new Todo("B", null);
        b.setId(a.getId());
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void marcarComoConcluidaRefleteNoToString() {
        Todo t = new Todo("X", null);
        t.setDone(true);
        assertTrue(t.isDone());
        assertTrue(t.toString().startsWith("[OK]"));
    }
}
