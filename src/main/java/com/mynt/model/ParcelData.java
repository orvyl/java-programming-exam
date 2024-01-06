package com.mynt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParcelData {

    @JsonProperty(value = "weight_kg", required = true)
    @NotNull(message = "weight_kg is required")
    private Double weightKg;

    @JsonProperty(value = "height_cm", required = true)
    @NotNull(message = "height_cm is required")
    private Double heightCm;

    @JsonProperty(value = "width_cm", required = true)
    @NotNull(message = "width_cm is required")
    private Double widthCm;

    @JsonProperty(value = "length_cm", required = true)
    @NotNull(message = "length_cm is required")
    private Double lengthCm;

    @JsonIgnore
    private Double volume;
}
