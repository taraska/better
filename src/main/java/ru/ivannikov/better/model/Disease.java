package ru.ivannikov.better.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "diseases")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Disease {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "disease", nullable = false, length = 50)
    private String disease;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private Patient patients;

    public Disease() {
        super();
    }

    public Disease(final String disease, final Patient patients) {
        this.disease = disease;
        this.patients = patients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Patient getPatients() {
        return patients;
    }

    public void setPatients(Patient patient) {
        this.patients = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disease disease1 = (Disease) o;
        Long id = (patients == null) ? null : patients.getId();
        Long oId = (disease1.patients == null) ? null : disease1.patients.getId();
        return Objects.equals(id, oId) &&
                Objects.equals(disease1.getDisease(), disease);
    }

    @Override
    public int hashCode() {
        Long id = (patients == null) ? null : patients.getId();
        return Objects.hash(id, disease);
    }
}
