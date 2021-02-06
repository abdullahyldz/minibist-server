package org.tasks;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SellTask {

    private final String operation = "sell";
    private String stockId;
    private String amount;
    private String userId;
    private String price;

    public SellTask(String stockId, String amount, String userId, String price) {
        this.stockId = stockId;
        this.amount = amount;
        this.userId = userId;
        this.price = price;
    }
}
