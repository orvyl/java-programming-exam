package com.mynt.model;

import lombok.Data;

@Data
public class ComputedCost {
    private Double cost;
    private Double discount;
    private boolean isRejected;
    private Double volume;
}
