package ru.ivannikov.better.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.ivannikov.better.model.Doctor;
import ru.ivannikov.better.service.DoctorService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@WebMvcTest(DoctorController.class)
public class DoctorControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DoctorService serviceMock;

    private DoctorData normalData;

    public static DoctorData getNormalDoctorData() {
        var doctorData = new DoctorData();
        doctorData.setId(2L);
        doctorData.setDepartment("testDepartment");

        var patientData = new PatientData();
        patientData.setId(3L);
        patientData.setFirstName("firstName");
        patientData.setLastName("lastName");

        Set<String> diseases = new HashSet<>();
        diseases.add("testDisease");
        patientData.setDiseases(diseases);

        Set<PatientData> patientDataSet = new HashSet<>();
        patientDataSet.add(patientData);
        doctorData.setPatients(patientDataSet);

        return doctorData;
    }

    @Before
    public void setUp() {
        this.normalData = getNormalDoctorData();
    }

    @Test
    public void saveDoctor200() throws Exception {
        var doctor = new Doctor();
        doctor.setId(1L);

        when(serviceMock.save(Mockito.any(DoctorData.class))).thenReturn(doctor);

        postMethod(200, this.normalData);
    }

    @Test
    public void saveDoctor500() throws Exception {
        when(serviceMock.save(Mockito.any(DoctorData.class))).thenReturn(null);

        postMethod(500, this.normalData);
    }

    @Test
    public void saveDoctorDepartmentNull400() throws Exception {
        this.normalData.setDepartment(null);

        var doctor = new Doctor();
        doctor.setId(1L);

        when(serviceMock.save(Mockito.any(DoctorData.class))).thenReturn(doctor);

        postMethod(400, this.normalData);
    }

    @Test
    public void saveDoctorPatientsNull400() throws Exception {
        this.normalData.setPatients(null);

        var doctor = new Doctor();
        doctor.setId(1L);

        when(serviceMock.save(Mockito.any(DoctorData.class))).thenReturn(doctor);

        postMethod(400, this.normalData);
    }

    private void postMethod(final int status, DoctorData doctorData) throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mvc.perform(post("/api/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(doctorData)))
                .andExpect(status().is(status));
    }
}
