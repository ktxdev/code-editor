package com.ktxdev.codeeditor.service;

import com.ktxdev.codeeditor.domain.CodeExecutionRequest;

public interface CodeExecutorService {
    CodeExecutor.CodeResult execute(CodeExecutionRequest codeExecutionRequest);
}
