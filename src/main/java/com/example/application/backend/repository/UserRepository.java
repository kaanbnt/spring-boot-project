package com.example.application.backend.repository;

import com.example.application.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("    Select user " +
            "   From User user" +
            "   where user.userName = :userName " +
            "   and user.passWord = :password")
    User findUserByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);
}
