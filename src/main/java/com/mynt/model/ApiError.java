package com.mynt.model;

import lombok.Value;

public record ApiError(String code, String message) {
}
