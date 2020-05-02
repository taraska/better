package ru.ivannikov.better.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.ivannikov.better.model.Doctor;
import ru.ivannikov.better.report.DocumentReports;
import ru.ivannikov.better.service.DoctorService;

import javax.validation.Valid;

@Controller
public class DoctorController {

    private DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @DocumentReports
    @PostMapping("/api/save")
    @ResponseBody
    public ResponseEntity<Doctor> saveDoctorByHttp(@Valid @RequestBody DoctorData data) {
        return saveDoctor(data);
    }

    @DocumentReports
    public ResponseEntity<Doctor> saveDoctorByFolder(@Valid @RequestBody DoctorData data) {
        return saveDoctor(data);
    }

    /**
     * Push doctor data to the service
     *
     * @param data doctor data
     * @return response entity with status 200 or 500
     */
    private ResponseEntity<Doctor> saveDoctor(final DoctorData data) {
        Doctor doctorData = doctorService.save(data);
        if (doctorData == null) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok(doctorData);
    }
}
