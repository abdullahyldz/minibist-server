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

@RequiredArgsConstructor
public class SignupTask {

    private final String operation = "signup";
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String errorMessage = "";

    // JSON parser object to parse read file
    private JSONParser jsonParser = new JSONParser();
    private JSONArray accounts = null;
    private JSONArray portfolios = null;

    public SignupTask(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jsonParser = new JSONParser();
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
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        // Could not find account in db
        initializeAccount();
        initializePortfolio();
        // saveAccounts(accounts);
        return true;
    }

    private void initializePortfolio() {
        if (portfolios == null) {
            portfolios = new JSONArray();
        }

        JSONObject obj = new JSONObject();
        obj.put("email", this.email);
        obj.put("money", "100");
        JSONArray arr = new JSONArray();
        obj.put("stocks", arr);

        try (FileReader reader = new FileReader("portfolio.json")) {
            // Read JSON file
            portfolios = (JSONArray) jsonParser.parse(reader);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        portfolios.add(obj);
        try (FileWriter file = new FileWriter("portfolio.json")) {
            file.write(portfolios.toJSONString());
            file.flush();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private void initializeAccount() {
        if (accounts == null) {
            accounts = new JSONArray();
        }

        JSONObject newAccount = new JSONObject();
        newAccount.put("email", this.email);
        newAccount.put("password", this.password);
        newAccount.put("fullname", this.firstName + " " + this.lastName);
        accounts.add(newAccount);
        try (FileWriter file = new FileWriter("accounts.json")) {
            file.write(accounts.toJSONString());
            file.flush();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
