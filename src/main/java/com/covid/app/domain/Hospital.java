package com.covid.app.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Hospital.
 */
@Entity
@Table(name = "hospital")
public class Hospital implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "street_address")
    private String streetAddress;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @OneToOne
    @JoinColumn(unique = true)
    private User headOfSearvice;

    @OneToMany(mappedBy = "hospital")
    private Set<Bed> beds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Hospital name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Hospital streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public Hospital city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User getHeadOfSearvice() {
        return headOfSearvice;
    }

    public Hospital headOfSearvice(User user) {
        this.headOfSearvice = user;
        return this;
    }

    public void setHeadOfSearvice(User user) {
        this.headOfSearvice = user;
    }

    public Set<Bed> getBeds() {
        return beds;
    }

    public Hospital beds(Set<Bed> beds) {
        this.beds = beds;
        return this;
    }

    public Hospital addBed(Bed bed) {
        this.beds.add(bed);
        bed.setHospital(this);
        return this;
    }

    public Hospital removeBed(Bed bed) {
        this.beds.remove(bed);
        bed.setHospital(null);
        return this;
    }

    public void setBeds(Set<Bed> beds) {
        this.beds = beds;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hospital)) {
            return false;
        }
        return id != null && id.equals(((Hospital) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Hospital{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", city='" + getCity() + "'" +
            "}";
    }
}
