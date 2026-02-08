package com.rash.Fashion.World.service;

import com.rash.Fashion.World.model.User;

public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;
}
