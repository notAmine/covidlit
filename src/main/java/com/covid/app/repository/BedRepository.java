package com.covid.app.repository;

import com.covid.app.domain.Bed;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Bed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BedRepository extends JpaRepository<Bed, Long> {
}
