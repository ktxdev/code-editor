package com.ktxdev.codeeditor.service;

import com.ktxdev.codeeditor.domain.CodeExecutionRequest;
import com.ktxdev.codeeditor.domain.Language;
import com.ktxdev.codeeditor.service.impl.CodeExecutorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class CodeExecutorServiceUnitTest {

    public CodeExecutorService codeExecutorService;

    @BeforeEach
    public void setUp() {
        codeExecutorService = new CodeExecutorServiceImpl();
    }

    @Test
    public void whenGivenValidJavaCode_thenShouldExecuteSuccessfully() {
        String code = "public class Solution {\n" +
                "\tpublic static void main(String[] args) {\n" +
                "\t\tSystem.out.println(\"Hello world! Just testing\");\n" +
                "\t}\n" +
                "}";

        val request = new CodeExecutionRequest(Language.JAVA, code, "HelloWorld.java");

        val result = codeExecutorService.execute(request);
        log.info("### Code executed result: {}", result);
        assertTrue(result.isSuccess());
    }

}