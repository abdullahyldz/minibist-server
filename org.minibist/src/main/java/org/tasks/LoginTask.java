package org.tasks;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginTask {
    
    private String email;
    private String password;

    public LoginTask(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
