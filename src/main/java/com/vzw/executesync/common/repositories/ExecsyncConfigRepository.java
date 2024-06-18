package com.vzw.executesync.common.repositories;

import com.vzw.executesync.common.entities.ExecsyncConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecsyncConfigRepository extends JpaRepository<ExecsyncConfig, Integer> {
}
