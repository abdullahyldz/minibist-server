package org.tasks;

import lombok.RequiredArgsConstructor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SignupTask {

    private final String operation = "signup";
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String errorMessage = "";

    public SignupTask(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void saveAccounts(JSONArray accounts) {
        if (accounts == null) {
            accounts = new JSONArray();
        }
        try (FileWriter file = new FileWriter("accounts.json")) {
            file.write(accounts.toJSONString());
            file.flush();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public boolean execute() {

        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        JSONArray accounts = null;
        JSONObject newAccount = new JSONObject();
        newAccount.put("email", this.email);
        newAccount.put("password", this.password);
        newAccount.put("fullname", this.firstName + " " + this.lastName);

        try (FileReader reader = new FileReader("accounts.json")) {
            // Read JSON file
            accounts = (JSONArray) jsonParser.parse(reader);
            JSONObject obj;

            System.out.println(accounts);
            for (Object account : accounts) {
                obj = (JSONObject) account;
                if (obj.get("email").equals(this.email)) {
                    this.errorMessage = "This email is already on the system.";
                    return false;
                }
            }
            accounts.add(newAccount);
        } catch (FileNotFoundException e) {
            JSONArray initialAccounts = new JSONArray();
            try (FileWriter file = new FileWriter("accounts.json")) {
                JSONObject initializerObject = new JSONObject();
                initializerObject.put("email", "asdasd@a.com");
                initializerObject.put("password", "asdasd");
                initializerObject.put("fullname", "test account");
                initialAccounts.add(initializerObject);
                file.write(initialAccounts.toJSONString());
                file.flush();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        saveAccounts(accounts);
        return true;
    }
}
