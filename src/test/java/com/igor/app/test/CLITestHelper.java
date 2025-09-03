package com.igor.app.test;

import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class CLITestHelper {
    public static String exec(Object command, String... args) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out, true, StandardCharsets.UTF_8);
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;
        try {
            System.setOut(ps);
            System.setErr(ps);
            new CommandLine(command).execute(args);
        } finally {
            System.setOut(oldOut);
            System.setErr(oldErr);
        }
        return out.toString(StandardCharsets.UTF_8);
    }
}
