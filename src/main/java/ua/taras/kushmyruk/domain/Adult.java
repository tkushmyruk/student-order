package ua.taras.kushmyruk.domain;

import java.time.LocalDate;

public class Adult extends Person{
    private String passportSeria;
    private String passportNumber;
    private LocalDate issueDate;
    private PassporOffice passportOffice;
    private University university;
    private String studentId;

    public Adult(String sirName, String givenName, String patronymic, LocalDate dateOfBirth) {
        super(sirName, givenName, patronymic, dateOfBirth);

    }
    public Adult(){

    }

    public String getPassportSeria() {
        return passportSeria;
    }

    public void setPassportSeria(String passportSeria) {
        this.passportSeria = passportSeria;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public PassporOffice getPassportOffice() {
        return passportOffice;
    }

    public void setPassporOffice(PassporOffice passportOffice) {
        this.passportOffice = passportOffice;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "Adult{" +
                "passportSeria='" + passportSeria + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", issueDate=" + issueDate +
                ", passportOffice=" + passportOffice +
                ", university=" + university +
                ", studentId='" + studentId + '\'' +
                "} " + super.toString();
    }
}
