package ru.ivannikov.better.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ivannikov.better.model.Disease;
import ru.ivannikov.better.model.Doctor;
import ru.ivannikov.better.model.Patient;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@DataJpaTest
public class DoctorRepositoryTest {
    @TestConfiguration
    public static class UserRepositoryTestContextConfiguration {
        @Bean
        public DoctorRepository doctorRepository(EntityManager entityManager) {
            return new DoctorRepository(entityManager);
        }
    }

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void findDiseasesByPatientTestSuccess() {
        Doctor doctor = doctorRepository.saveDoctor(getNewDoctor());

        Set<Patient> patients = doctor.getPatients();
        Patient patient = patients.stream().filter(Objects::nonNull).findFirst().get();
        List<Disease> diseasesByPatient = doctorRepository.findDiseasesByPatient(patient);

        Assert.assertFalse(diseasesByPatient.isEmpty());

    }

    @Test
    public void findDiseasesByPatientTestFailed() {
        final Patient randomPatient = new Patient();
        randomPatient.setId(11L);
        List<Disease> diseasesByPatient = doctorRepository.findDiseasesByPatient(randomPatient);

        Assert.assertTrue(diseasesByPatient.isEmpty());
    }

    @Test
    public void saveNewDoctorSuccess() {
        final Doctor doctor = doctorRepository.saveDoctor(getNewDoctor());
        Assert.assertNotNull(doctor);
    }

    @Test
    public void saveExistsDoctorSuccess() {
        final Doctor newDoctor = getNewDoctor();
        newDoctor.setId(10L);
        Doctor doctorAfterRepository = doctorRepository.saveDoctor(newDoctor);

        final String department = doctorAfterRepository.getDepartment();
        newDoctor.setDepartment("anotherDepartment");
        Doctor doctorAfterChange = doctorRepository.saveDoctor(newDoctor);

        Assert.assertNotEquals(doctorAfterChange.getDepartment(), department);
    }

    private Doctor getNewDoctor() {
        final Doctor doctor = new Doctor("testDepartment");
        final Patient patient = new Patient("firstName", "lastName");
        final Disease disease = new Disease();

        disease.setDisease("testDisease");
        disease.setPatients(patient);
        Set<Disease> diseaseSet = new HashSet<>();
        diseaseSet.add(disease);

        patient.setDiseases(diseaseSet);
        Set<Patient> patientSet = new HashSet<>();
        patientSet.add(patient);

        doctor.setDepartment("testDepartment");
        doctor.setPatients(patientSet);

        return doctor;
    }
}
