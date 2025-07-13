package com.google_file_storage.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.google_file_storage.Entity.Folder;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByUserId(Long userId);
}
