package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.pet.PetType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Pet", catalog = "critter")
public class Pet implements Serializable {

    @Id
    @SequenceGenerator(name="PET_SEQ", sequenceName="PET_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="PET_SEQ")
    @Column(name = "PET_ID", unique = true, nullable = false)
    private long id;

    @Nationalized
    @Column(name = "PET_NAME",nullable = true,length = 255)
    private String name;


    @Enumerated(EnumType.STRING)
    @Column(name = "PET_TYPE",length = 255)
    private PetType petType;

    @Column(name = "PET_NOTES",length = 500)
    private String notes;

    @Column(name = "BIRTH_DATE")
    private LocalDate birth_date;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer pet_owner;

    public Pet() {

    }

    public Pet(long id, String name, PetType petType, String notes, LocalDate birth_date, Customer pet_owner) {
        this.id = id;
        this.name = name;
        this.petType = petType;
        this.notes = notes;
        this.birth_date = birth_date;
        this.pet_owner = pet_owner;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPet_owner(Customer pet_owner) {
        this.pet_owner = pet_owner;
    }

    public Customer getPet_owner() {
        return pet_owner;
    }
}
