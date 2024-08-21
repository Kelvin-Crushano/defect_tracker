package com.sgic.semita.repositories;

import com.sgic.semita.entities.DensityRange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DensityRangeRepository extends JpaRepository<DensityRange, Long> {

    List<DensityRange> findAllByProjectId(Long projectId);
}


