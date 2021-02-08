package org.tasks;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import com.google.gson.JsonArray;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.models.Prices;

@RequiredArgsConstructor
public class BuyTask {

    private final String operation = "buy";
    private String stockName;
    private Integer amount;
    private String email;
    private Integer price;
    private String errorMessage = "";
    @Setter
    private static Lock accountReadLock;
    @Setter
    private static Lock accountWriteLock;
    @Setter
    private static Lock portfolioReadLock;
    @Setter
    private static Lock portfolioWriteLock;

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

    private void savePortfolios() {
        if (portfolios == null) {
            portfolios = new JSONArray();
        }
        portfolioReadLock.unlock();
        portfolioWriteLock.lock();
        try (FileWriter file = new FileWriter("portfolio.json")) {
            file.write(portfolios.toJSONString());
            file.flush();
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally {
            portfolioWriteLock.unlock();
            portfolioReadLock.lock();
        }
    }

    public boolean execute() {

        JSONObject obj = null;
        if (portfolios == null) {
            portfolios = new JSONArray();
        }
        portfolioReadLock.lock();
        try (FileReader reader = new FileReader("portfolio.json")) {
            // Read JSON file
            portfolios = (JSONArray) jsonParser.parse(reader);
            System.out.println(portfolios);

            for (Object portfolio : portfolios) {
                obj = (JSONObject) portfolio;
                if (obj.get("email").equals(this.email)) {
                    this.errorMessage = "";
                    Integer money = ((Long) obj.get("money")).intValue();
                    if (isValid(money, this.price * this.amount)) {
                        obj.put("money", money - this.price * this.amount);
                        JSONArray stocks = (JSONArray) obj.get("stocks");
                        JSONObject stockObj = new JSONObject();
                        boolean found = false;
                        for (Object stockObject : stocks) {
                            stockObj = (JSONObject) stockObject;
                            if (((String) stockObj.get("name")).equals(this.stockName)) {
                                stockObj.put("name", this.stockName);
                                stockObj.put("amount", ((Long) stockObj.get("amount")).intValue() + this.amount);
                                Prices.setPrice(stockName, this.price);
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            stockObj = new JSONObject();
                            stockObj.put("name", this.stockName);
                            stockObj.put("amount", this.amount);
                            stocks.add(stockObj);
                            obj.put("stocks", stocks);
                            Prices.setPrice(stockName, this.price);
                        }
                        savePortfolios();
                        return true;
                    } else {
                        this.errorMessage = "Not sufficient money OR Cannot buy from this price";
                        return false;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            portfolioReadLock.unlock();
            portfolioWriteLock.lock();
            try (FileWriter file = new FileWriter("portfolio.json")) {
                JSONArray accounts = new JSONArray();
                JSONObject newAccount = new JSONObject();
                newAccount.put("email", this.email);
                newAccount.put("money", 100);
                newAccount.put("stocks", new JSONArray());
                accounts.add(newAccount);
                this.errorMessage = "A problem has occurred. Please try again";
                file.write(accounts.toJSONString());
                file.flush();
            } catch (IOException e2) {
                e2.printStackTrace();
            } finally {
                portfolioWriteLock.unlock();
                portfolioReadLock.lock();
            }
        } catch (Exception e) {
            return false;
        } finally {
            portfolioReadLock.unlock();
        }
        return false;
    }

    private boolean isValid(Integer money, Integer excess) {
        return money >= excess && Prices.isBuyValid(this.price, this.stockName);
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
