package com.rash.Fashion.World.response;

import com.rash.Fashion.World.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private USER_ROLE role;
}
