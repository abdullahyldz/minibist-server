package org.tasks;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Task {
    
    private String operation;
    private String message;

    public Task(String operation, String message){
        this.message = message;
        this.operation = operation;
    }
}
