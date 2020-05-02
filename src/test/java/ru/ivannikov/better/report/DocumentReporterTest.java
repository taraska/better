package ru.ivannikov.better.report;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ivannikov.better.controller.DoctorControllerTest;
import ru.ivannikov.better.model.Doctor;
import ru.ivannikov.better.repository.DocumentReportRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DocumentReporterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    private DocumentReporter documentReporter;

    private DocumentReportRepository documentReportRepository;

    private Doctor doctor;

    @Before
    public void setUp() {
        doctor = new Doctor();
        doctor.setId(10L);

        documentReportRepository = mock(DocumentReportRepository.class);
        documentReporter = new DocumentReporter(documentReportRepository);
    }

    @Test
    public void makeReportTestHttpSuccess() throws Throwable {
        makeReport("saveDoctorByHttp", new ResponseEntity<>(this.doctor, HttpStatus.OK));
    }

    @Test
    public void makeReportTestFolderSuccess() throws Throwable {
        makeReport("saveDoctorByFolder", new ResponseEntity<>(this.doctor, HttpStatus.OK));
    }

    @Test
    public void makeReportTestStatus400Failed() throws Throwable {
        makeReport("saveDoctorByFolder", new ResponseEntity<>(this.doctor, HttpStatus.BAD_REQUEST));
    }

    @Test(expected = RuntimeException.class)
    public void makeReportTestRuntimeExceptionFailed() throws Throwable {
        when(proceedingJoinPoint.getSignature()).thenReturn(new Signature() {
            @Override
            public String toShortString() {
                return null;
            }

            @Override
            public String toLongString() {
                return null;
            }

            @Override
            public String getName() {
                return "test";
            }

            @Override
            public int getModifiers() {
                return 0;
            }

            @Override
            public Class getDeclaringType() {
                return null;
            }

            @Override
            public String getDeclaringTypeName() {
                return null;
            }
        });
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[]{(DoctorControllerTest.getNormalDoctorData())});

        when(proceedingJoinPoint.proceed()).thenThrow(new RuntimeException("RuntimeException"));

        documentReporter.makeReport(proceedingJoinPoint);
    }

    private void makeReport(final String signatureName, final Object object) throws Throwable {
        when(proceedingJoinPoint.getSignature()).thenReturn(new Signature() {
            @Override
            public String toShortString() {
                return null;
            }

            @Override
            public String toLongString() {
                return null;
            }

            @Override
            public String getName() {
                return signatureName;
            }

            @Override
            public int getModifiers() {
                return 0;
            }

            @Override
            public Class getDeclaringType() {
                return null;
            }

            @Override
            public String getDeclaringTypeName() {
                return null;
            }
        });
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[]{(DoctorControllerTest.getNormalDoctorData())});

        when(proceedingJoinPoint.proceed()).thenReturn(object);

        documentReporter.makeReport(proceedingJoinPoint);
    }

}
