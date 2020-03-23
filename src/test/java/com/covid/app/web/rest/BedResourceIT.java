package com.covid.app.web.rest;

import com.covid.app.CovidlitApp;
import com.covid.app.domain.Bed;
import com.covid.app.repository.BedRepository;
import com.covid.app.service.BedService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.covid.app.domain.enumeration.BedStatus;
/**
 * Integration tests for the {@link BedResource} REST controller.
 */
@SpringBootTest(classes = CovidlitApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BedResourceIT {

    private static final BedStatus DEFAULT_STATUS = BedStatus.AVAILABLE;
    private static final BedStatus UPDATED_STATUS = BedStatus.RESERVED;

    @Autowired
    private BedRepository bedRepository;

    @Autowired
    private BedService bedService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBedMockMvc;

    private Bed bed;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bed createEntity(EntityManager em) {
        Bed bed = new Bed()
            .status(DEFAULT_STATUS);
        return bed;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bed createUpdatedEntity(EntityManager em) {
        Bed bed = new Bed()
            .status(UPDATED_STATUS);
        return bed;
    }

    @BeforeEach
    public void initTest() {
        bed = createEntity(em);
    }

    @Test
    @Transactional
    public void createBed() throws Exception {
        int databaseSizeBeforeCreate = bedRepository.findAll().size();

        // Create the Bed
        restBedMockMvc.perform(post("/api/beds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bed)))
            .andExpect(status().isCreated());

        // Validate the Bed in the database
        List<Bed> bedList = bedRepository.findAll();
        assertThat(bedList).hasSize(databaseSizeBeforeCreate + 1);
        Bed testBed = bedList.get(bedList.size() - 1);
        assertThat(testBed.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createBedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bedRepository.findAll().size();

        // Create the Bed with an existing ID
        bed.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBedMockMvc.perform(post("/api/beds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bed)))
            .andExpect(status().isBadRequest());

        // Validate the Bed in the database
        List<Bed> bedList = bedRepository.findAll();
        assertThat(bedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBeds() throws Exception {
        // Initialize the database
        bedRepository.saveAndFlush(bed);

        // Get all the bedList
        restBedMockMvc.perform(get("/api/beds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bed.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getBed() throws Exception {
        // Initialize the database
        bedRepository.saveAndFlush(bed);

        // Get the bed
        restBedMockMvc.perform(get("/api/beds/{id}", bed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bed.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBed() throws Exception {
        // Get the bed
        restBedMockMvc.perform(get("/api/beds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBed() throws Exception {
        // Initialize the database
        bedService.save(bed);

        int databaseSizeBeforeUpdate = bedRepository.findAll().size();

        // Update the bed
        Bed updatedBed = bedRepository.findById(bed.getId()).get();
        // Disconnect from session so that the updates on updatedBed are not directly saved in db
        em.detach(updatedBed);
        updatedBed
            .status(UPDATED_STATUS);

        restBedMockMvc.perform(put("/api/beds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBed)))
            .andExpect(status().isOk());

        // Validate the Bed in the database
        List<Bed> bedList = bedRepository.findAll();
        assertThat(bedList).hasSize(databaseSizeBeforeUpdate);
        Bed testBed = bedList.get(bedList.size() - 1);
        assertThat(testBed.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingBed() throws Exception {
        int databaseSizeBeforeUpdate = bedRepository.findAll().size();

        // Create the Bed

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBedMockMvc.perform(put("/api/beds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bed)))
            .andExpect(status().isBadRequest());

        // Validate the Bed in the database
        List<Bed> bedList = bedRepository.findAll();
        assertThat(bedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBed() throws Exception {
        // Initialize the database
        bedService.save(bed);

        int databaseSizeBeforeDelete = bedRepository.findAll().size();

        // Delete the bed
        restBedMockMvc.perform(delete("/api/beds/{id}", bed.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bed> bedList = bedRepository.findAll();
        assertThat(bedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
