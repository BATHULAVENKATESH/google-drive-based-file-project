package com.google_file_storage.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.google_file_storage.Entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByUserId(Long userId);
}