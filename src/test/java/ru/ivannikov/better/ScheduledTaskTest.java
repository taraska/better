package ru.ivannikov.better;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import ru.ivannikov.better.controller.DoctorController;
import ru.ivannikov.better.controller.DoctorData;
import ru.ivannikov.better.model.Doctor;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class ScheduledTaskTest {

    @InjectMocks
    private ScheduledTask scheduledTask;

    @Mock
    private DoctorController doctorController;


    final private File input = new File("src/test/resources/input/");

    final private File out = new File("src/test/resources/out/");

    final private File error = new File("src/test/resources/error/");


    @Before
    public void setUp() {
        ReflectionTestUtils.setField(scheduledTask, "input", this.input);
        ReflectionTestUtils.setField(scheduledTask, "out", this.out);
        ReflectionTestUtils.setField(scheduledTask, "error", this.error);
    }

    @After
    public void moveTestFilesAfterTests() {
        File[] xmlFilesOut = out.listFiles((dir, name) -> name.endsWith(".xml"));
        File[] xmlFilesError = error.listFiles((dir, name) -> name.endsWith(".xml"));
        this.move(xmlFilesError);
        this.move(xmlFilesOut);
    }

    @Test
    public void scheduleXmlDoctorTestOK() throws IOException {

        when(doctorController.saveDoctorByFolder(Mockito.any(DoctorData.class)))
                .thenReturn(new ResponseEntity<>(new Doctor(), HttpStatus.OK));

        scheduledTask.scheduleXmlDoctor();

        Assert.assertEquals(true, inDirectory(out));
    }

    @Test
    public void scheduleXmlDoctorTestBadRequest() throws IOException {
        when(doctorController.saveDoctorByFolder(Mockito.any(DoctorData.class)))
                .thenReturn(new ResponseEntity<>(new Doctor(), HttpStatus.BAD_REQUEST));

        scheduledTask.scheduleXmlDoctor();

        Assert.assertEquals(true, inDirectory(error));
    }

    private boolean inDirectory(final File directory) {
        Optional<File> directoryOptional = Optional.of(directory);

        return directoryOptional.
                filter(o -> directory.isDirectory()).
                filter(o -> Objects.requireNonNull(directory.list()).length > 0)
                .isPresent();
    }

    private void move(final File[] xmlFiles) {
        for (File currentFile :
                Objects.requireNonNull(xmlFiles)) {
            new File(currentFile.getPath()).renameTo(new File(input.getPath() + "/" + currentFile.getName()));

        }
    }
}
