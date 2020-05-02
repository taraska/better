package ru.ivannikov.better.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class PatientData {
    @NotNull(message = "Please provide an id")
    @Min(1)
    private Long id;

    @JsonProperty("first_name")
    @JacksonXmlProperty(localName = "first_name")
    @NotEmpty(message = "Please provide a name")
    @Size(min = 1, max = 50)
    private String firstName;

    @JsonProperty("last_name")
    @JacksonXmlProperty(localName = "last_name")
    @NotEmpty(message = "Please provide a last name")
    @Size(min = 1, max = 50)
    private String lastName;

    @NotEmpty(message = "Please provide diseases")
    private Set<String> diseases;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<String> getDiseases() {
        return diseases;
    }

    public void setDiseases(Set<String> diseases) {
        this.diseases = diseases;
    }
}
