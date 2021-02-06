package org.tasks;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuyTask {

    private final String operation = "buy";
    private String stockId;
    private String amount;
    private String userId;
    private String price;

    public BuyTask(String stockId, String amount, String userId, String price) {
        this.stockId = stockId;
        this.amount = amount;
        this.userId = userId;
        this.price = price;
    }

}
