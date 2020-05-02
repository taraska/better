package ru.ivannikov.better.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor implements Identifiable<Long> {

    @Id
    @Column(name = "id")
    @GenericGenerator(
            name = "assigned-identity",
            strategy = "ru.ivannikov.better.model.AssignedIdentityGenerator"
    )
    @GeneratedValue(
            generator = "assigned-identity",
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(name = "department", nullable = false, length = 50)
    private String department;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "doctors_patients",
            joinColumns = {@JoinColumn(name = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "patient_id")}
    )
    private Set<Patient> patients;

    public Doctor() {
        super();
    }

    public Doctor(final String department) {
        this.department = department;
    }

    public Doctor(final String department, final Set<Patient> patients) {
        this.department = department;
        this.patients = patients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }
}
