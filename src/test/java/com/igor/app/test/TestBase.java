package com.igor.app.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

public abstract class TestBase {
    private String oldHome;

    @BeforeEach
    void setUp(@TempDir Path tempHome) {
        oldHome = System.getProperty("user.home");
        System.setProperty("user.home", tempHome.toString());
    }

    @AfterEach
    void tearDown() {
        System.setProperty("user.home", oldHome);
    }
}
