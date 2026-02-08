package com.rash.Fashion.World.repository;

import com.rash.Fashion.World.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    //<entity,unique identifier type(id)>

    //JPA methods
    public User findByEmail(String username);
}
