package com.lpms.service.repository;

import com.lpms.service.entity.Plate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The interface Plate repository.
 */
public interface PlateRepository extends CrudRepository<Plate, Integer> {
    /**
     * Find by reg containing list.
     *
     * @param reg the reg
     * @return the list
     */
    List<Plate> findByRegContaining(String reg);

    /**
     * Find by reg containing and style list.
     *
     * @param reg   the reg
     * @param style the style
     * @return the list
     */
    List<Plate> findByRegContainingAndStyle(String reg, Integer style);
}
