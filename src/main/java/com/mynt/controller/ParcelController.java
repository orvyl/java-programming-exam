package com.mynt.controller;

import com.mynt.VoucherException;
import com.mynt.model.ComputedCost;
import com.mynt.model.ParcelData;
import com.mynt.service.ParcelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("parcel")
public class ParcelController {

    private final ParcelService parcelService;

    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @PostMapping("cost")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    private ComputedCost computeCost(
            @RequestBody @Valid ParcelData parcelData,
            @RequestParam(name = "voucher-code", required = false) String voucherCode) throws VoucherException {
        return parcelService.computeParcelCost(parcelData, voucherCode);
    }

}
