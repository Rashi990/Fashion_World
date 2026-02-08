package com.rash.Fashion.World.service.serviceImp;

import com.rash.Fashion.World.config.JwtProvider;
import com.rash.Fashion.World.model.User;
import com.rash.Fashion.World.repository.UserRepository;
import com.rash.Fashion.World.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if (user==null){
            throw new Exception("User not found");
        }

        return user;
    }
}
