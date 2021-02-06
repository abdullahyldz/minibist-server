package org.tasks;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lombok.RequiredArgsConstructor;

public class LoginTask {

    private String email;
    private String password;
    private String errorMessage = "";

    public LoginTask(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean execute() {

        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

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
                JSONObject initializerObject = new JSONObject();
                initializerObject.put("email", "asdasd@a.com");
                initializerObject.put("password", "asdasd");
                accountsArray.add(initializerObject);
                file.write(accountsArray.toJSONString());
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
        return true;
    }
}
