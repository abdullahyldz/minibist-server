package org.tasks;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Task {

    private String operation;
    private String message;

    public Task(String operation, String message) {
        this.message = message;
        this.operation = operation;
    }
}
