package com.lpms.service.entity.request;

import lombok.Data;

@Data
public class CustomerCreateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
