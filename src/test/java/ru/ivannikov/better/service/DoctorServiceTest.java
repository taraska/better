package ru.ivannikov.better.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ivannikov.better.controller.DoctorControllerTest;
import ru.ivannikov.better.controller.DoctorData;
import ru.ivannikov.better.model.Doctor;
import ru.ivannikov.better.repository.DoctorRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DoctorServiceTest {

    private DoctorService doctorService;
    
    private DoctorRepository doctorRepository;

    private DoctorData normalData;

    @Before
    public void setUp() {
        doctorRepository = mock(DoctorRepository.class);
        doctorService = new DoctorService(doctorRepository);

        this.normalData = DoctorControllerTest.getNormalDoctorData();
    }

    @Test
    public void saveSuccess() {
        final var doctor = new Doctor();
        doctor.setId(30L);

        when(doctorRepository.saveDoctor(Mockito.any(Doctor.class))).thenReturn(doctor);

        Doctor save = doctorService.save(this.normalData);

        Assert.assertNotNull(save);
    }

    @Test(expected = NullPointerException.class)
    public void saveFailed() {
        final var doctor = new Doctor();
        doctor.setId(30L);

        this.normalData.setPatients(null);

        when(doctorRepository.saveDoctor(Mockito.any(Doctor.class))).thenReturn(doctor);

        doctorService.save(this.normalData);
    }

}
