package com.lpms.service.entity.request;

import lombok.Data;

@Data
public class PlateChangePriceRequest {
    private Integer plateId;
    private String price;
}
