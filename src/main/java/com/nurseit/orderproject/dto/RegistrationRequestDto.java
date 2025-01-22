package com.nurseit.orderproject.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class RegistrationRequestDto {
    private String username;
    private String password;
}