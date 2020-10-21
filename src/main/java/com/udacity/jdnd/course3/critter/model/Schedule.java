package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Schedule",catalog = "critter")
public class Schedule implements Serializable {

    @Id
    @SequenceGenerator(name="SCHEDULE_SEQ", sequenceName="SCHEDULE_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="SCHEDULE_SEQ")
    @Column(name = "SCHEDULE_ID", unique = true, nullable = false)
    private long id;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "ACTIVITIES", length = 500)
    private Set<EmployeeSkill> activities = new HashSet<>();

    @Column(name = "SCHEDULE_DATE")
    private LocalDate date;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SCHEDULE_EMPLOYEE",joinColumns = @JoinColumn(name = "SCHEDULE_ID"),inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    private List<Employee> employeeIds = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SCHEDULE_PET",joinColumns = @JoinColumn(name = "SCHEDULE_ID"),inverseJoinColumns = @JoinColumn(name = "PET_ID"))
    private List<Pet> petIds = new ArrayList<>();

    public Schedule() {

    }

    public Schedule(long id, Set<EmployeeSkill> activities, LocalDate date, List<Employee> employeeIds, List<Pet> petIds) {
        this.id = id;
        this.activities = activities;
        this.date = date;
        this.employeeIds = employeeIds;
        this.petIds = petIds;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setEmployees(List<Employee> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Employee> getEmployees() {
        return employeeIds;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setPets(List<Pet> petIds) {
        this.petIds = petIds;
    }

    public List<Pet> getPets() {
        return petIds;
    }
}
