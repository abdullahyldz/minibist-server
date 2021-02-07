package org.tasks;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class LoginTask {

    private String email;
    private String password;
    private String errorMessage = "";
    @Setter
    private static Lock accountReadLock;
    @Setter
    private static Lock accountWriteLock;
    @Setter
    private static Lock portfolioReadLock;
    @Setter
    private static Lock portfolioWriteLock;

    public LoginTask(String email, String password) {
        this.email = email;
        this.password = password;
        this.errorMessage = "";
    }

    public boolean execute() {

        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        accountReadLock.lock();
        try (FileReader reader = new FileReader("accounts.json")) {
            // Read JSON file
            JSONArray accounts = (JSONArray) jsonParser.parse(reader);
            JSONObject obj;

            System.out.println(accounts);
            for (Object account : accounts) {
                obj = (JSONObject) account;
                if (obj.get("email").equals(this.email)) {
                    if (obj.get("password").equals(this.password)) {
                        this.errorMessage = "";
                        return true;
                    } else {
                        this.errorMessage = "Please check your credentials.";
                        return false;
                    }
                }
            }
            return false;

        } catch (FileNotFoundException e) {
            JSONArray accountsArray = new JSONArray();
            try (FileWriter file = new FileWriter("accounts.json")) {
                file.write(accountsArray.toJSONString());
                file.flush();
                return false;
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            accountReadLock.unlock();
        }
        return true;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
