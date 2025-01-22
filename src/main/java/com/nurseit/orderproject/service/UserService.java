package com.nurseit.orderproject.service;

import com.nurseit.orderproject.dto.LoginRequestDto;
import com.nurseit.orderproject.dto.LoginResponseDto;
import com.nurseit.orderproject.dto.RegistrationRequestDto;
import com.nurseit.orderproject.entity.User;

public interface UserService {
    void registration(RegistrationRequestDto registrationRequest);
    LoginResponseDto login(LoginRequestDto loginRequest);
}
