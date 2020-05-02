package ru.ivannikov.better.repository;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ivannikov.better.model.*;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@DataJpaTest
public class DocumentReportRepositoryTest {
    @TestConfiguration
    public static class UserRepositoryTestContextConfiguration {
        @Bean
        public DocumentReportRepository documentReportRepository(EntityManager entityManager) {
            return new DocumentReportRepository(entityManager);
        }
    }

    @Autowired
    private DocumentReportRepository documentReportRepository;

    @Test
    public void documentReportRepositorySaveSuccess() {
        var documentReport = new DocumentReport(1L, 2L, "no error", DocumentSource.HTTP);
        documentReportRepository.saveDocumentReport(documentReport);
    }

    @Test(expected = ConstraintViolationException.class)
    public void documentReportRepositorySaveFailed() {
        var documentReport = new DocumentReport(1L, 2L, "no error", null);
        documentReportRepository.saveDocumentReport(documentReport);
    }
}
