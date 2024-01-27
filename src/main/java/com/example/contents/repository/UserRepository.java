package com.example.contents.repository;

import com.example.contents.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // null 일 가능성을 피하기 위해 Optional?
    Boolean existsByUsername(String username);
}
