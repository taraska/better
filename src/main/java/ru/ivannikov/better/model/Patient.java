package ru.ivannikov.better.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "patients")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Patient implements Identifiable<Long> {

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

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @ManyToMany(mappedBy = "patients")
    private Set<Doctor> doctors;

    @JsonManagedReference
    @OneToMany(mappedBy = "patients", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Disease> diseases;

    public Patient() {
        super();
    }

    public Patient(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Patient(final String firstName, final String lastName, final Set<Doctor> doctors) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.doctors = doctors;
    }

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

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public Set<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(Set<Disease> diseases) {
        this.diseases = diseases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id.equals(patient.id) &&
                firstName.equals(patient.firstName) &&
                lastName.equals(patient.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
