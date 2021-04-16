package com.lpms.service.entity.request;

import lombok.Data;

@Data
public class OrderTransferRequest {
    private String email;
    private Integer orderId;
}
