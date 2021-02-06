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
public class BuyTask {

    private final String operation = "buy";
    private String stockName;
    private Integer amount;
    private String email;
    private Integer price;
    private String errorMessage = "";
    // JSON parser object to parse read file
    JSONParser jsonParser = new JSONParser();
    JSONArray portfolios = null;

    public BuyTask(String stockName, Integer amount, String email, Integer price) {
        this.stockName = stockName;
        this.amount = amount;
        this.email = email;
        this.price = price;
        this.jsonParser = new JSONParser();
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    private void saveAccounts(JSONArray accounts) {
        if (accounts == null) {
            accounts = new JSONArray();
        }
        try (FileWriter file = new FileWriter("portfolio.json")) {
            file.write(accounts.toJSONString());
            file.flush();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public boolean execute() {

        JSONObject obj = null;

        try (FileReader reader = new FileReader("portfolio.json")) {
            // Read JSON file
            portfolios = (JSONArray) jsonParser.parse(reader);
            System.out.println(portfolios);

            for (Object portfolio : portfolios) {
                obj = (JSONObject) portfolio;
                if (obj.get("email").equals(this.email)) {
                    this.errorMessage = "";
                    Integer money = Integer.parseInt((String) obj.get("money"));
                    if (isValid(money, this.price * this.amount)) {
                        obj.put("money", Integer.toString(money - this.price * this.amount));
                    } else {
                        this.errorMessage = "Not sufficient money";
                        return false;
                    }
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
        initializeAccount();

        initializeAccount();

        obj = new JSONObject();
        obj.put("email", this.email);
        if (isValid(100, this.price * this.amount)) {
            obj.put("money", Integer.toString(100 - this.price * this.amount));
            portfolios.add(obj);
            saveAccounts(portfolios);
            return true;
        } else {
            obj.put("money", "100");
            portfolios.add(obj);
            saveAccounts(portfolios);
            this.errorMessage = "Not sufficient money";
            return false;
        }

        return true;
    }

    private void initializeAccount() {
        if (portfolios == null) {
            portfolios = new JSONArray();
        }
        JSONObject obj = new JSONObject();
        obj = new JSONObject();
        obj.put("email", this.email);
        obj.put("money", "100");
        portfolios.add(obj);
        try (FileWriter file = new FileWriter("accounts.json")) {
            file.write(portfolios.toJSONString());
            file.flush();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private boolean isValid(Integer money, Integer excess) {
        return money >= excess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
