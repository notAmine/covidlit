package com.covid.app.service;

import com.covid.app.domain.Bed;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Bed}.
 */
public interface BedService {

    /**
     * Save a bed.
     *
     * @param bed the entity to save.
     * @return the persisted entity.
     */
    Bed save(Bed bed);

    /**
     * Get all the beds.
     *
     * @return the list of entities.
     */
    List<Bed> findAll();

    /**
     * Get the "id" bed.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Bed> findOne(Long id);

    /**
     * Delete the "id" bed.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
