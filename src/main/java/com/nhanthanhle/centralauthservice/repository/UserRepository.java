package com.nhanthanhle.centralauthservice.repository;


import com.nhanthanhle.centralauthservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsById(String id);
    boolean existsByUsername(String username);

    Optional<User> findUserByUsername(String username);
}

