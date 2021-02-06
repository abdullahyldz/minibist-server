package org.tasks;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuyTask {

    private final String operation = "buy";
    private String stockName;
    private String amount;
    private String userId;
    private String price;

    public BuyTask(String stockName, String amount, String userId, String price) {
        this.stockName = stockName;
        this.amount = amount;
        this.userId = userId;
        this.price = price;
    }

}
