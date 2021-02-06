package ua.taras.kushmyruk.domain;

import java.time.LocalDate;

public class Child extends Person {
    private String certificateNumber;
    private LocalDate issueDate;
    private RegisterOffice registerOffice;

    public Child(String sirName, String givenName, String patronymic, LocalDate dateOfBirth) {
        super(sirName, givenName, patronymic, dateOfBirth);
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public RegisterOffice getRegisterOffice() {
        return registerOffice;
    }

    public void setRegisterOffice(RegisterOffice registerOffice) {
        this.registerOffice = registerOffice;
    }

    @Override
    public String toString() {
        return "Child{" +
                "certificateNumber='" + certificateNumber + '\'' +
                ", issueDate=" + issueDate +
                ", registerOffice=" + registerOffice +
                "} " + super.toString();
    }
}
