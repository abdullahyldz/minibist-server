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

    public BuyTask(String stockId, String amount, String userId){
        this.stockId = stockId;
        this.amount = amount;
        this.userId = userId;
    }

}
