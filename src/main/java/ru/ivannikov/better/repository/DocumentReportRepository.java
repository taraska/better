package ru.ivannikov.better.repository;

import org.springframework.stereotype.Repository;
import ru.ivannikov.better.model.DocumentReport;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public class DocumentReportRepository extends HibernateSupport {

    public DocumentReportRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Transactional
    public void saveDocumentReport(DocumentReport documentReport) {
        getSession().save(documentReport);
    }
}
