package ru.ivannikov.better;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.ivannikov.better.model.Doctor;
import ru.ivannikov.better.controller.DoctorController;
import ru.ivannikov.better.controller.DoctorData;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Component
public class ScheduledTask {

    @Autowired
    private DoctorController doctorController;

    @Value("${properties.input}")
    private File input;

    @Value("${properties.out}")
    private File out;

    @Value("${properties.error}")
    private File error;


    /**
     * Proceed via xml
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void scheduleXmlDoctor() throws IOException {
        File[] xmlFiles = input.listFiles((dir, name) -> name.endsWith(".xml"));

        for (File file :
                Objects.requireNonNull(xmlFiles)) {
            var xmlMapper = new XmlMapper();
            DoctorData doctorData = xmlMapper.readValue(file, DoctorData.class);
            ResponseEntity<Doctor> doctorResponseEntity = doctorController.saveDoctorByFolder(doctorData);

            if (doctorResponseEntity.getStatusCode().isError()) {
                move(file, error);
            } else {
                move(file, out);
            }
        }
    }

    /**
     * Move file
     *
     * @param file file for move
     * @param dir  destination
     */
    private void move(File file, File dir) {
        new File(file.getPath()).renameTo(new File(dir.getPath() + "/" + file.getName()));
    }
}
