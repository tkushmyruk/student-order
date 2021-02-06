package ua.taras.kushmyruk.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentOrder {
    private long studentOrderId;
    private LocalDateTime studetnOrderDate;
    private StudentOrderStatus studentOrderStatus;
    private Adult husband;
    private Adult wife;
    private List<Child> children;
    private String marriageCertificatedId;
    private LocalDate marriageDate;
    private RegisterOffice marriageOffice;

    public long getStudentOrderId() {
        return studentOrderId;
    }

    public void setStudentOrderId(long studentOrderId) {
        this.studentOrderId = studentOrderId;
    }

    public Adult getHusband() {
        return husband;
    }

    public void setHusband(Adult husband) {
        this.husband = husband;
    }

    public Adult getWife() {
        return wife;
    }

    public void setWife(Adult wife) {
        this.wife = wife;
    }

    public List<Child> getChildren() {
        return children;
    }
    public void addChild(Child child){
        if(children == null){
            children = new ArrayList<>(5);
        }
            children.add(child);
    }

    public String getMarriageCertificatedId() {
        return marriageCertificatedId;
    }

    public void setMarriageCertificatedId(String marriageCertificatedId) {
        this.marriageCertificatedId = marriageCertificatedId;
    }

    public LocalDate getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(LocalDate marriageDate) {
        this.marriageDate = marriageDate;
    }

    public RegisterOffice getMarriageOffice() {
        return marriageOffice;
    }

    public void setMarriageOffice(RegisterOffice marriageOffice) {
        this.marriageOffice = marriageOffice;
    }

    public LocalDateTime getStudetnOrderDate() {
        return studetnOrderDate;
    }

    public void setStudetnOrderDate(LocalDateTime studetnOrderDate) {
        this.studetnOrderDate = studetnOrderDate;
    }

    public StudentOrderStatus getStudentOrderStatus() {
        return studentOrderStatus;
    }

    public void setStudentOrderStatus(StudentOrderStatus studentOrderStatus) {
        this.studentOrderStatus = studentOrderStatus;
    }

    @Override
    public String toString() {
        return "StudentOrder{" +
                "studentOrderId=" + studentOrderId +
                ", studetnOrderDate=" + studetnOrderDate +
                ", studentOrderStatus=" + studentOrderStatus +
                ", husband=" + husband +
                ", wife=" + wife +
                ", children=" + children +
                ", marriageCertificatedId='" + marriageCertificatedId + '\'' +
                ", marriageDate=" + marriageDate +
                ", marriageOffice=" + marriageOffice +
                '}';
    }
}
