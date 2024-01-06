package com.mynt.service;

import com.mynt.VoucherException;
import com.mynt.model.ComputedCost;
import com.mynt.model.ParcelData;
import com.mynt.model.Voucher;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class ParcelService {

    private final KieContainer kieContainer;
    private final RestTemplate restTemplate;

    @Value("${voucher.endpoint}")
    private String voucherUrl;

    public ParcelService(KieContainer kieContainer, RestTemplate restTemplate) {
        this.kieContainer = kieContainer;
        this.restTemplate = restTemplate;
    }

    public ComputedCost computeParcelCost(ParcelData parcelData, String voucherCode) throws VoucherException {
        ComputedCost computedCost = new ComputedCost();

        double volume = parcelData.getHeightCm() * parcelData.getWidthCm() * parcelData.getLengthCm();
        computedCost.setVolume(volume);
        parcelData.setVolume(volume);

        KieSession session = kieContainer.newKieSession();
        session.setGlobal("computedCost", computedCost);
        session.insert(parcelData);
        session.fireAllRules();
        session.dispose();

        if (computedCost.isRejected() || Objects.isNull(voucherCode) || voucherCode.isBlank()) {
            return computedCost;
        }

        return computeCostWithVoucher(voucherCode, computedCost);
    }

    private ComputedCost computeCostWithVoucher(String voucherCode, ComputedCost computedCost) throws VoucherException {
        try {
            Voucher voucher = restTemplate.getForObject(String.format(voucherUrl, voucherCode), Voucher.class);
            /*
             * for offline voucher service

            Voucher voucher = new Voucher();
            voucher.setDiscount(1.0);
            voucher.setExpiry(LocalDate.now().minusDays(3));
            voucher.setCode(voucherCode);
             */

            assert voucher != null;
            computedCost.setVoucher(voucher);

            LocalDate now = LocalDate.now();
            LocalDate expiryDate = voucher.getExpiry();
            if (!expiryDate.isBefore(now)) {
                computedCost.setCostWithDiscount(computedCost.getCost() - voucher.getDiscount());
            }

            return computedCost;
        } catch (HttpClientErrorException ex) {
            throw new VoucherException("Failed to retrieve voucher: " + voucherCode + ". " + ex.getStatusText(), ex);
        }
    }
}
