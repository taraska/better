package ru.ivannikov.better.report;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.ivannikov.better.model.Doctor;
import ru.ivannikov.better.model.DocumentReport;
import ru.ivannikov.better.model.DocumentSource;
import ru.ivannikov.better.repository.DocumentReportRepository;
import ru.ivannikov.better.controller.DoctorData;

@Aspect
@Component
public class DocumentReporter {

    private static final Logger logger = LoggerFactory.getLogger(DocumentReports.class);

    private DocumentReportRepository documentReportRepository;

    public DocumentReporter(DocumentReportRepository documentReportRepository) {
        this.documentReportRepository = documentReportRepository;
    }

    @Pointcut("@annotation(ru.ivannikov.better.report.DocumentReports)")
    public void report() {
    }

    /**
     * Save a report of processing Doctors Data
     *
     * @param joinPoint aop point
     * @return response entity to the doctor controller
     * @throws Throwable any Runtime Exception
     */
    @Around("report()")
    public Object makeReport(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("making a report...");

        final long startTime = System.currentTimeMillis();
        final String methodName = joinPoint.getSignature().getName();

        var documentSource = DocumentSource.HTTP;
        if (methodName.equals("saveDoctorByFolder")) {
            documentSource = DocumentSource.FOLDER;
        }

        Long doctorId = getDoctorId(joinPoint.getArgs());
        String errorMessage = "no error";
        Long time;

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (RuntimeException e) {
            logger.error("RuntimeException in report: ", e);
            errorMessage = e.getMessage();
            time = getTime(startTime);
            var documentReport = new DocumentReport(time, doctorId, errorMessage, documentSource);
            saveDocumentReport(documentReport);

            throw e;
        }

        time = getTime(startTime);

        ResponseEntity<Doctor> doctorResponseEntity = ((ResponseEntity<Doctor>) result);

        if (doctorResponseEntity.getStatusCode().isError()) {
            errorMessage = doctorResponseEntity.getStatusCode().getReasonPhrase();
        } else {
            doctorId = doctorResponseEntity.getBody().getId();
        }

        var documentReport = new DocumentReport(time, doctorId, errorMessage, documentSource);
        saveDocumentReport(documentReport);

        return result;
    }

    private long getDoctorId(final Object[] args) {
        if (args.length > 0) {
            DoctorData doctorData = (DoctorData) args[0];
            return doctorData.getId();
        }
        return 0;
    }

    private void saveDocumentReport(final DocumentReport documentReport) {
        documentReportRepository.saveDocumentReport(documentReport);
    }

    private Long getTime(final Long startTime) {
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

}
