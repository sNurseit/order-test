package com.nurseit.orderproject.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class LoginRequestDto {
    private String username;
    private String password;
}
