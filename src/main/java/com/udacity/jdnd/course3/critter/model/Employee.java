package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Employee",catalog = "critter")
public class Employee implements Serializable {

    @Id
    @SequenceGenerator(name="EMPLOYEE_SEQ", sequenceName="EMPLOYEE_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="EMPLOYEE_SEQ")
    @Column(name = "EMPLOYEE_ID", unique = true, nullable = false)
    private long id;

    @Nationalized
    @Column(name = "EMPLOYEE_NAME" ,nullable = true, length = 255)
    private String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "EMPLOYEE_SKILLS",length = 500)
    private List<EmployeeSkill> skills = new ArrayList<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "DAYS_AVAILABLE", length = 500)
    private List<DayOfWeek> daysAvailable = new ArrayList<>();

    public Employee() {

    }

    public Employee(long id, String name, List<EmployeeSkill> skills, List<DayOfWeek> daysAvailable) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.daysAvailable = daysAvailable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setSkills(List<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public List<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setDaysAvailable(List<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
