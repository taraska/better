package ru.ivannikov.better.service;

import org.springframework.stereotype.Service;
import ru.ivannikov.better.model.Disease;
import ru.ivannikov.better.model.Doctor;
import ru.ivannikov.better.model.Patient;
import ru.ivannikov.better.repository.DoctorRepository;
import ru.ivannikov.better.controller.DoctorData;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class DoctorService {

    private DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    /**
     * Cascade save doctor to database
     *
     * @param data doctor model with patients and diseases
     * @return this Doctor
     */
    public Doctor save(final DoctorData data) {

        var doctor = new Doctor();
        doctor.setId(data.getId());
        doctor.setDepartment(data.getDepartment());

        Set<Patient> patientSet = new HashSet<>();

        data.getPatients().forEach(patientData -> {
            var patient = new Patient();
            patient.setId(patientData.getId());
            patient.setLastName(patientData.getLastName());
            patient.setFirstName(patientData.getFirstName());

            Set<Disease> diseases = new HashSet<>();

            patientData.getDiseases().forEach(dis -> {
                Disease disease = new Disease();
                disease.setPatients(patient);
                disease.setDisease(dis);
                diseases.add(disease);
            });

            List<Disease> diseasesExists = repository.findDiseasesByPatient(patient);
            Set<Disease> diseaseSet = new HashSet<>(diseasesExists);
            diseases.removeAll(diseaseSet);

            patient.setDiseases(diseases);
            patientSet.add(patient);
        });

        doctor.setPatients(patientSet);

        return repository.saveDoctor(doctor);
    }
}
