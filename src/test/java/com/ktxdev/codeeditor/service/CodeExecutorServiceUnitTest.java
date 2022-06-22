package com.ktxdev.codeeditor.service;

import com.ktxdev.codeeditor.domain.CodeExecutionRequest;
import com.ktxdev.codeeditor.domain.Language;
import com.ktxdev.codeeditor.service.impl.CodeExecutorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

    @Test
    public void whenGivenInvalidJavaCode_thenShouldNotExecuteSuccessfully() {
        String code = "public class Solution {\n" +
                "\tpublic static void main(String[] args) {\n" +
                "\t\tSystem.out.println(\"Hello world! Just testing\")\n" +
                "\t}\n" +
                "}";

        val request = new CodeExecutionRequest(Language.JAVA, code, "HelloWorld.java");

        val result = codeExecutorService.execute(request);
        log.info("### Code executed result: {}", result);
        assertFalse(result.isSuccess());
    }

    @Test
    public void whenGivenValidPythonCode_thenShouldExecuteSuccessfully() {
        String code = "print(\"Hello from Python\")";

        val request = new CodeExecutionRequest(Language.PYTHON3, code, "hello.py");

        val result = codeExecutorService.execute(request);
        log.info("### Code executed result: {}", result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void whenGivenInValidPythonCode_thenShouldNotExecuteSuccessfully() {
        String code = "print(\"Hello from Python\"";

        val request = new CodeExecutionRequest(Language.PYTHON3, code, "hello.py");

        val result = codeExecutorService.execute(request);
        log.info("### Code executed result: {}", result);
        assertFalse(result.isSuccess());
    }

    @Test
    public void whenGivenValidJavaScriptCode_thenShouldExecuteSuccessfully() {
        String code = "console.log(\"Hello from JavaScript\")";

        val request = new CodeExecutionRequest(Language.JAVASCRIPT, code, "script.js");

        val result = codeExecutorService.execute(request);
        log.info("### Code executed result: {}", result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void whenGivenValidJavaScriptCode_thenShouldNotExecuteSuccessfully() {
        String code = "cons.log(\"Hello from JavaScript\")";

        val request = new CodeExecutionRequest(Language.JAVASCRIPT, code, "script.js");

        val result = codeExecutorService.execute(request);
        log.info("### Code executed result: {}", result);
        assertFalse(result.isSuccess());
    }
}