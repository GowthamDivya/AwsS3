package com.vzw.executesync.common.repositories;


import com.vzw.executesync.common.entities.FileIngestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileIngestionRepository extends JpaRepository<FileIngestion, Integer> {
}
