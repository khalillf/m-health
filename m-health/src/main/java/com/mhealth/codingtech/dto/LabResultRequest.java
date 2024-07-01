package com.mhealth.codingtech.dto;

public class LabResultRequest {
    private Long id_patient;
    private String testName;
    private String resultValue;
    private String resultDate;
    private String notes;

    public Long getId_patient() {
        return id_patient;
    }

    public void setId_patient(Long id_patient) {
        this.id_patient = id_patient;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
