package org.tasks;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.google.gson.Gson;
import org.Response;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TaskHandler {

    private static final String success = "success";
    private static final String failure = "failure";
    private static final String invalid = "invalid";
    private static final String loginSuccessMsg = "Completed login";
    private static final String signupSuccessMsg = "Completed signup";
    private static final String buySuccessMsg = "Completed buy operation";
    private static final String sellSuccessMsg = "Completed sell operation";

    private static final String loginFailureMsg = "Invalid credentials";
    private static final String signupFailureMsg = "Signup failed";
    private static final String buyFailureMsg = "Buying failed";
    private static final String sellFailureMsg = "Selling failed";
    private static final ReadWriteLock accountLock = new ReentrantReadWriteLock();
    private static final ReadWriteLock portfolioLock = new ReentrantReadWriteLock();

    public static String handleMessage(Gson gson, String msg) {
        System.out.println(msg);
        Task task = gson.fromJson(msg, Task.class);
        Response response = null;

        if (task.getOperation() == null || task.getMessage() == null) {
            System.out.println(invalid);
        } else if (task.getOperation().equals("signup")) {
            SignupTask signupTask = gson.fromJson(task.getMessage(), SignupTask.class);
            signupTask.setAccountLock(accountLock);
            signupTask.setPortfolioLock(portfolioLock);
            if (signupTask.execute()) {
                response = Response.builder().status(success).message(signupSuccessMsg).build();
            } else {
                response = Response.builder().status(failure)
                        .message(signupTask.getErrorMessage() != null && signupTask.getErrorMessage() != ""
                                ? signupTask.getErrorMessage()
                                : signupFailureMsg)
                        .build();
            }
        } else if (task.getOperation().equals("login")) {
            LoginTask loginTask = gson.fromJson(task.getMessage(), LoginTask.class);
            loginTask.setAccountLock(accountLock);
            loginTask.setPortfolioLock(portfolioLock);
            if (loginTask.execute()) {
                response = Response.builder().status(success).message(loginSuccessMsg).build();
            } else {
                response = Response.builder().status(failure)
                        .message(loginTask.getErrorMessage() != null && loginTask.getErrorMessage() != ""
                                ? loginTask.getErrorMessage()
                                : loginFailureMsg)
                        .build();
            }
        } else if (task.getOperation().equals("buy")) {
            BuyTask buyTask = gson.fromJson(task.getMessage(), BuyTask.class);
            buyTask.setAccountLock(accountLock);
            buyTask.setPortfolioLock(portfolioLock);
            if (buyTask.execute()) {
                response = Response.builder().status(success).message(buySuccessMsg).build();
            } else {
                response = Response.builder().status(failure)
                        .message(buyTask.getErrorMessage() != null && buyTask.getErrorMessage() != ""
                                ? buyTask.getErrorMessage()
                                : buyFailureMsg)
                        .build();
            }
        } else if (task.getOperation().equals("sell")) {
            SellTask sellTask = gson.fromJson(task.getMessage(), SellTask.class);
            sellTask.setAccountLock(accountLock);
            sellTask.setPortfolioLock(portfolioLock);
            if (sellTask.execute()) {
                response = Response.builder().status(success).message(sellSuccessMsg).build();
            } else {
                response = Response.builder().status(failure)
                        .message(sellTask.getErrorMessage() != null && sellTask.getErrorMessage() != ""
                                ? sellTask.getErrorMessage()
                                : sellFailureMsg)
                        .build();
            }
        } else {
            System.out.println("Operation not valid.");
            response = Response.builder().status(failure).message(invalid).build();
        }
        return gson.toJson(response);
    }
}
