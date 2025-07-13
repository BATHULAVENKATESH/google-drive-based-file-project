package com.google_file_storage.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.google_file_storage.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

