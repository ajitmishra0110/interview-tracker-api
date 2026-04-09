package com.ajit.interviewtracker.user.repository;

import com.ajit.interviewtracker.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}