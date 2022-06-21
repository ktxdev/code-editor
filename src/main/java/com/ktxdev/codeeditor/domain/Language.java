package com.ktxdev.codeeditor.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Language {
    JAVA("java"),
    PYTHON3("python"),
    JAVASCRIPT("node");

    private final String command;
}
