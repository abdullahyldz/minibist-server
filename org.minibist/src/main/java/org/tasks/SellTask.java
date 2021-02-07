package org.tasks;

import lombok.RequiredArgsConstructor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@RequiredArgsConstructor
public class SellTask {

    private final String operation = "sell";
    private String stockName;
    private Integer amount;
    private String email;
    private Integer price;
    private String errorMessage = "";
    // JSON parser object to parse read file
    JSONParser jsonParser = new JSONParser();
    JSONArray portfolios = null;

    public SellTask(String stockName, String Integer, String email, Integer price) {
        this.stockName = stockName;
        this.amount = amount;
        this.email = email;
        this.price = price;
        this.jsonParser = new JSONParser();

    }

    private void savePortfolios() {
        if (portfolios == null) {
            portfolios = new JSONArray();
        }
        try (FileWriter file = new FileWriter("portfolio.json")) {
            file.write(portfolios.toJSONString());
            file.flush();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public boolean execute() {

        JSONObject obj = null;
        if (portfolios == null) {
            portfolios = new JSONArray();
        }

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
                        savePortfolios();
                        return true;
                    } else {
                        this.errorMessage = "Not sufficient money";
                        return false;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            try (FileWriter file = new FileWriter("portfolio.json")) {
                JSONArray accounts = new JSONArray();
                JSONObject newAccount = new JSONObject();
                newAccount.put("email", this.email);
                newAccount.put("money", "100");
                accounts.add(newAccount);
                this.errorMessage = "A problem has occurred. Please try again";
                file.write(accounts.toJSONString());
                file.flush();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private boolean isValid(Integer money, Integer excess) {
        return money >= excess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
