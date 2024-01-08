package com.mynt.controller;

import com.mynt.VoucherException;
import com.mynt.model.ApiError;
import com.mynt.model.ComputedCost;
import com.mynt.model.ParcelData;
import com.mynt.service.ParcelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @ExceptionHandler(VoucherException.class)
    public ResponseEntity<ApiError> handlerVoucherException(VoucherException e) {
        return ResponseEntity
                .internalServerError()
                .body(new ApiError("HTTP-500", e.getMessage()));
    }
}
