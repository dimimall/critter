package com.udacity.jdnd.course3.critter.model;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Customer",catalog = "critter")
public class Customer implements Serializable {

    @Id
    @SequenceGenerator(name="CUSTOMER_SEQ", sequenceName="CUSTOMER_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="CUSTOMER_SEQ")
    @Column(name = "CUSTOMER_ID", unique = true, nullable = false)
    private long id;

    @Nationalized
    @Column(name = "CUSTOMER_NAME", length = 255)
    private String name;

    @Column(name = "PHONE_NUMBER", length = 255)
    private String phoneNumber;

    @Column(name = "CUSTOMER_NOTES", length = 500)
    private String notes;

    @OneToMany(mappedBy = "pet_owner",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pet> listpet = new ArrayList<>();

    public Customer() {}

    public Customer(long id, String name, String phoneNumber, String notes, List<Pet> listpet) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.listpet = listpet;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phonenumber) {
        this.phoneNumber = phonenumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getListpet() {
        return listpet;
    }

    public void setListpet(List<Pet> listpet) {
        this.listpet = listpet;
    }
}
