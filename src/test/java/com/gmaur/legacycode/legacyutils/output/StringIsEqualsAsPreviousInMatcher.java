package com.gmaur.legacycode.legacyutils.output;

/*
 *	LegacyUtils is a set of tools for dealing with legacy code
 *
 *	Copyright (C) 2017 G Maur (gmaur.com)
 *
 *	Subject to terms and condition provided in LICENSE
 *
 *	Acknowledgments: Rachel
 */

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StringIsEqualsAsPreviousInMatcher extends TypeSafeDiagnosingMatcher<String> {

    private String fileName;

    public StringIsEqualsAsPreviousInMatcher(String fileName) {
        this.fileName = fileName;
    }

    public static StringIsEqualsAsPreviousInMatcher isEqualsAsPreviousIn(String fileName) {
        return new StringIsEqualsAsPreviousInMatcher(fileName);
    }

    @Override
    protected boolean matchesSafely(String consoleOutput, Description description) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(fileName);
            if (resource == null) {
                description.appendText("One first execution is required");
                writeConsoleOutputInFile(consoleOutput);
                return false;
            }
            String previousFilePathString = resource.getPath();
            Path previousFilePath = Paths.get(previousFilePathString);
            String previousFileContent = getStringFrom(previousFilePath);
            assertThat(consoleOutput, is(previousFileContent));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a string with the same content as " + fileName);
    }

    private String getStringFrom(Path previousFilePath) throws IOException {
        return new String(Files.readAllBytes(previousFilePath));
    }

    private void writeConsoleOutputInFile(String consoleOutput) throws IOException {
        Path path = Paths.get("src", "test", "resources", fileName);
        try (FileWriter fw = new FileWriter(path.toString(), false);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(consoleOutput);
        }
    }
}