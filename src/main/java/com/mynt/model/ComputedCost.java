package com.mynt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ComputedCost {
    private String type;
    private Double cost;
    private Double volume;
    private Voucher voucher;

    @JsonIgnore
    private boolean isRejected;
}
