package org.tasks;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SellTask {

    private final String operation = "sell";
    private String stockName;
    private String amount;
    private String userId;
    private String price;

    public SellTask(String stockName, String amount, String userId, String price) {
        this.stockName = stockName;
        this.amount = amount;
        this.userId = userId;
        this.price = price;
    }

    public boolean execute() {
        return true;
    }
}
