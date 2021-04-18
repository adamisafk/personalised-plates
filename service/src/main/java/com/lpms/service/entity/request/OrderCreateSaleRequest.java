package com.lpms.service.entity.request;

import lombok.Data;

@Data
public class OrderCreateSaleRequest {
    private Integer prevOrderId;
    private Integer plateId;
    private String price;
}
