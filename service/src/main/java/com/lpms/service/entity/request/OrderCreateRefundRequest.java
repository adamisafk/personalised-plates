package com.lpms.service.entity.request;

import lombok.Data;

@Data
public class OrderCreateRefundRequest {
    private Integer orderId;
    private Integer plateId;
}
