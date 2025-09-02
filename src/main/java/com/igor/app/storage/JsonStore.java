package com.igor.app.storage;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.igor.app.model.Todo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class JsonStore {
    private final Path filePath;
    private final Gson gson;
    private final Type listType = new TypeToken<List<Todo>>(){}.getType();

    public JsonStore() {
        Path dir = Paths.get(System.getProperty("user.home"), ".taskcli");
        this.filePath = dir.resolve("todos.json");
        try {
            Files.createDirectories(dir);
            if (!Files.exists(filePath)) Files.writeString(filePath, "[]", StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível inicializar " + dir, e);
        }
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (el, t, ctx) -> LocalDate.parse(el.getAsString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (src, t, ctx) -> new JsonPrimitive(src.toString()))
                .create();
    }

    public List<Todo> load() {
        try {
            String content = Files.readString(filePath, StandardCharsets.UTF_8).trim();
            if (content.isEmpty()) content = "[]";
            List<Todo> list = gson.fromJson(content, listType);
            return list != null ? list : new ArrayList<>();
        } catch (IOException | JsonSyntaxException e) {
            return new ArrayList<>();
        }
    }

    public void save(List<Todo> todos) {
        try {
            Files.writeString(filePath, gson.toJson(todos, listType), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar em " + filePath, e);
        }
    }

    public Path getFilePath() { return filePath; }
}
