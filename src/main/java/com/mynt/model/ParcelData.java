package com.mynt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParcelData {

    @JsonProperty(value = "weight_kg", required = true)
    private Double weightKg;

    @JsonProperty(value = "height_cm", required = true)
    private Double heightCm;

    @JsonProperty(value = "width_cm", required = true)
    private Double widthCm;

    @JsonProperty(value = "length_cm", required = true)
    private Double lengthCm;

    @JsonIgnore
    private Double volume;
}
