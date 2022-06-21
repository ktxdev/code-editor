package com.ktxdev.codeeditor.service.impl;

import com.ktxdev.codeeditor.domain.CodeExecutionRequest;
import com.ktxdev.codeeditor.exceptions.InvalidRequestException;
import com.ktxdev.codeeditor.service.CodeExecutor;
import com.ktxdev.codeeditor.service.CodeExecutorService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.ktxdev.codeeditor.service.CodeExecutor.CodeResult;

@Slf4j
@Service
public class CodeExecutorServiceImpl implements CodeExecutorService {
    @Override
    public CodeResult execute(CodeExecutionRequest codeExecutionRequest) {
        log.info("### Processing request: {}", codeExecutionRequest);
        Path codeFilePath = null;
        try {
            String fileName = codeExecutionRequest.getFileName();

            if (!fileName.endsWith(".java") && !fileName.endsWith(".js") && !fileName.endsWith(".py")) {
                throw new InvalidRequestException("This editor currently supports .java, .js and .py files only. " +
                        "Please check your filename.");
            }

            codeFilePath = Files.createFile(Path.of(fileName));
            log.info("### File path: {}", codeFilePath.toAbsolutePath());

            try(val outputStream = new BufferedOutputStream(Files.newOutputStream(codeFilePath))) {
                outputStream.write(codeExecutionRequest.getCode().getBytes());
                outputStream.flush();

                return CodeExecutor.execute(codeExecutionRequest.getLanguage(), codeFilePath);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (codeFilePath != null) {
                log.info("### Deleting file...");
                try {
                    Files.deleteIfExists(codeFilePath);
                } catch (IOException e) {
                    log.info("### Failed to delete file: {}", codeFilePath.toAbsolutePath());
                }
            }
        }
    }
}
