package com.lpms.service.repository;

import com.lpms.service.entity.Plate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlateRepository extends CrudRepository<Plate, Integer> {
    List<Plate> findByRegContaining(String reg);
    List<Plate> findByRegContainingAndStyle(String reg, Integer style);
}
