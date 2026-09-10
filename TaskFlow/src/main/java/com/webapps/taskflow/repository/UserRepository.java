package com.webapps.taskflow.repository;

import com.webapps.taskflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
