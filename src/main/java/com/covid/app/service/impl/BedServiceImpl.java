package com.covid.app.service.impl;

import com.covid.app.service.BedService;
import com.covid.app.domain.Bed;
import com.covid.app.repository.BedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Bed}.
 */
@Service
@Transactional
public class BedServiceImpl implements BedService {

    private final Logger log = LoggerFactory.getLogger(BedServiceImpl.class);

    private final BedRepository bedRepository;

    public BedServiceImpl(BedRepository bedRepository) {
        this.bedRepository = bedRepository;
    }

    /**
     * Save a bed.
     *
     * @param bed the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Bed save(Bed bed) {
        log.debug("Request to save Bed : {}", bed);
        return bedRepository.save(bed);
    }

    /**
     * Get all the beds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Bed> findAll() {
        log.debug("Request to get all Beds");
        return bedRepository.findAll();
    }

    /**
     * Get one bed by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Bed> findOne(Long id) {
        log.debug("Request to get Bed : {}", id);
        return bedRepository.findById(id);
    }

    /**
     * Delete the bed by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bed : {}", id);
        bedRepository.deleteById(id);
    }
}
