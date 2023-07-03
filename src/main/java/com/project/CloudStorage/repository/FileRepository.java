package com.project.CloudStorage.repository;

import com.project.CloudStorage.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    @Query(value = "SELECT file_id FROM files s WHERE s.user_id = :user_id", nativeQuery = true)
    List<Long> findAllFileIdByUserId(Long user_id);
}