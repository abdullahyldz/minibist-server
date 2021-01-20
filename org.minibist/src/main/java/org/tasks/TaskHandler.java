package org.tasks;

import com.google.gson.Gson;

import org.Response;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TaskHandler {
    
    public static String handleMessage(Gson gson, String msg) {
        System.out.println(msg);
        Task task = gson.fromJson(msg, Task.class);
        Response response = null;
        
        if(task.getOperation()==null ||  task.getMessage() == null) {
            System.out.println("Invalid operation");
        }
        else if(task.getOperation().equals("signup")){
            SignupTask signupTask = gson.fromJson(task.getMessage(), SignupTask.class);
            response = Response.builder().status("success").message("completed signup").build();
        } 
        else if(task.getOperation().equals("login")){
            LoginTask loginTask = gson.fromJson(task.getMessage(), LoginTask.class);
            response = Response.builder().status("success").message("completed login").build();

        } 
        // else if(task.getOperation().equals("buy")){
        //     BuyTask buyTask = gson.fromJson(task.getMessage(), BuyTask.class);
        // response = Response.builder().status("success").message("completed buy operation").build();
        // }
        // else if(task.getOperation().equals("sell")){
        //     SellTask sellTask = gson.fromJson(task.getMessage(), SellTask.class);
            //     response = Response.builder().status("success").message("completed sell").build();
        // } else if(task.getOperation().equals("cancel")){
        //     CancelTask cancelTask = gson.fromJson(task.getMessage(), CancelTask.class);
        //     response = Response.builder().status("success").message("completed cancel").build();
        // } 
        else {
            System.out.println("Operation not valid.");
            response = Response.builder().status("failure").message("Invalid operation").build();
        }
        return gson.toJson(response);   
    }
}
