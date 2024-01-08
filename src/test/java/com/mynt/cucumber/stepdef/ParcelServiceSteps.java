package com.mynt.cucumber.stepdef;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.ApplicationTests;
import com.mynt.model.ComputedCost;
import com.mynt.model.ParcelData;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class ParcelServiceSteps extends ApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private ComputedCost result = null;

    @Before
    public void cleanup() {
        result = null;
    }

    @DataTableType
    public ParcelData convertToParcelData(final Map<String, String> row) {
        ParcelData parcelData = new ParcelData();
        parcelData.setHeightCm(Double.valueOf(row.get("height_cm")));
        parcelData.setLengthCm(Double.valueOf(row.get("length_cm")));
        parcelData.setWidthCm(Double.valueOf(row.get("width_cm")));
        parcelData.setWeightKg(Double.valueOf(row.get("weight_kg")));

        return parcelData;
    }

    @When("a valid request sent with voucher {string}")
    public void sentRequest(String voucher, List<ParcelData> parcelData) {
        log.info("Sending request ...");

        String url = "http://localhost:8080/parcel/cost";
        url = Objects.nonNull(voucher) && !voucher.isBlank() ? url+"?voucher-code="+voucher:url;
        ResponseEntity<ComputedCost> entity = restTemplate.postForEntity(url, parcelData.get(0), ComputedCost.class);

        result = entity.getBody();
    }

    @Then("it should compute correctly with type {string} and cost {double}")
    public void itShouldComputeCorrectly(String type, Double cost) {
        Assert.assertEquals(type, result.getType());
        Assert.assertEquals(cost, result.getCost());
    }
}
