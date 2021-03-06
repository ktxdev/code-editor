package com.ktxdev.codeeditor.service;

import com.ktxdev.codeeditor.domain.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

@Slf4j
public class CodeExecutor {
    @Data
    @AllArgsConstructor
    public static class CodeResult {
        private boolean success;
        private String text;
    }

    public static CodeResult execute(Language language, Path path) throws IOException {
        Runtime runtime = Runtime.getRuntime();

        String absoluteFilePath = path.toAbsolutePath().toString();
        String[] command = {language.getCommand(), absoluteFilePath};

        Process process = runtime.exec(command);

        val result = processResult(process.getInputStream());

        if (StringUtils.hasText(result)) {
            return new CodeResult(true, result);
        }

        val processResult = processResult(process.getErrorStream());

        String pathToReplace = absoluteFilePath.substring(0, absoluteFilePath.lastIndexOf(path.normalize().toString()));
        return new CodeResult(false, processResult.replaceAll(pathToReplace, ""));
    }

    private static String processResult(InputStream inputStream) throws IOException {
        val reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        return builder.toString();
    }
}
