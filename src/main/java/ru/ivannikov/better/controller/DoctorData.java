package ru.ivannikov.better.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Set;

public class DoctorData {
    @JsonProperty("id")
    @JacksonXmlProperty(isAttribute = true)
    @NotNull(message = "Please provide an id")
    @Min(1)
    private Long id;

    @JsonProperty("department")
    @JacksonXmlProperty(isAttribute = true)
    @NotEmpty(message = "Please provide a department")
    @Size(min = 1, max = 50)
    private String department;

    @NotEmpty(message = "Please provide patients")
    @Valid
    private Set<PatientData> patients;

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

    public Set<PatientData> getPatients() {
        return patients;
    }

    public void setPatients(Set<PatientData> patientData) {
        this.patients = patientData;
    }
}
