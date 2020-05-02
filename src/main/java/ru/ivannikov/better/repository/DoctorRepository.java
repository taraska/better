package ru.ivannikov.better.repository;

import org.springframework.stereotype.Repository;
import ru.ivannikov.better.model.Disease;
import ru.ivannikov.better.model.Doctor;
import ru.ivannikov.better.model.Patient;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class DoctorRepository extends HibernateSupport {

    public DoctorRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public Doctor saveDoctor(Doctor doctor) {
        if (doctor.getId() != null) {
            entityManager.merge(doctor);
        } else entityManager.persist(doctor);

        entityManager.flush();

        return doctor;
    }

    public List<Disease> findDiseasesByPatient(final Patient patient) {
        return entityManager.createQuery("select d from Disease d " +
                "where d.patients.id = :patientId", Disease.class)
                .setParameter("patientId", patient.getId())
                .getResultList();
    }
}
