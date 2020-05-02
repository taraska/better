package ru.ivannikov.better.model;

import javax.persistence.*;

@Entity
@Table(name = "document_report")
public class DocumentReport {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "execution_time", nullable = false)
    private Long executionTime;

    @Column(name = "doctor_id", nullable = false)
    private Long doctor_id;

    @Column(name = "error", nullable = false)
    private String error;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_source", nullable = false, length = 10)
    private DocumentSource documentSource;


    public DocumentReport() {
        super();
    }

    public DocumentReport(final Long executionTime,
                          final Long doctor_id,
                          final String error,
                          final DocumentSource documentSource) {
        this.executionTime = executionTime;
        this.doctor_id = doctor_id;
        this.error = error;
        this.documentSource = documentSource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public Long getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(Long doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public DocumentSource getDocumentSource() {
        return documentSource;
    }

    public void setDocumentSource(DocumentSource documentSource) {
        this.documentSource = documentSource;
    }
}
