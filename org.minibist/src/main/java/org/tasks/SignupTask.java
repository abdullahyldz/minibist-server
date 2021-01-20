package org.tasks;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SignupTask {
    
    private final String operation = "signup";
    private String email;
    private String password;
    private String name;

    public SignupTask(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

}
