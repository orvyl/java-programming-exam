package com.mynt.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Voucher {
    private String code;
    private Double discount;
    private LocalDate expiry;
}
