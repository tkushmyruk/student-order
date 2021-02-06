package ua.taras.kushmyruk.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.taras.kushmyruk.domain.*;
import ua.taras.kushmyruk.exceptions.DaoExceptions;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

public class StudentOrderDaoImplTest {

    @BeforeClass
    public static void init() throws IOException, URISyntaxException {
        DbInit.startup();
    }


    @Test
    public void saveStudentOrder() throws DaoExceptions {
        StudentOrder studentOrder = buildStudentOrder(10);
       Long id =  new StudentOrderDaoImpl().saveStudentOrder(studentOrder);
    }
    @Test
    public void testSaveStudentOrderException()  {
        StudentOrder studentOrder = buildStudentOrder(12);
        studentOrder.getHusband().setSirName(null);
        try {
            new StudentOrderDaoImpl().saveStudentOrder(studentOrder);
            Assert.fail("should be error");
        } catch (DaoExceptions daoExceptions) {

        }
    }

    @Test(expected = DaoExceptions.class)
    public void testSaveStudentOrderExceptionAnotherWay() throws DaoExceptions {
        StudentOrder studentOrder = buildStudentOrder(44);
        studentOrder.getHusband().setSirName(null);
        Long id = new StudentOrderDaoImpl().saveStudentOrder(studentOrder);
    }



    @Test
    public void getStudentOrders() throws DaoExceptions {
        List<StudentOrder> studentOrders = new StudentOrderDaoImpl().getStudentOrders();
        Assert.assertEquals(1, studentOrders.size());
    }

     static StudentOrder buildStudentOrder(long id){
        StudentOrder studentOrder = new StudentOrder();
        studentOrder.setStudentOrderId(id);
        studentOrder.setMarriageCertificatedId("" + (1234560000 + id));
        studentOrder.setMarriageDate(LocalDate.of(2016, 7 , 4));
        RegisterOffice registerOffice = new RegisterOffice( 1l, "", "");
        studentOrder.setMarriageOffice(registerOffice);
        Street street = new Street(1, "first Street");

        Address address = new Address(street, "150", "12", "", "142");

        Adult husband = new Adult("Petrov", "Victor", "Sergeevych",
                LocalDate.of(1997,8,24));
        husband.setPassportSeria("" + (1000 + id));
        husband.setPassportNumber("" + (100000 + id));
        husband.setIssueDate(LocalDate.of(2017,9,15));
        PassporOffice passporOffice = new PassporOffice(1l, "", "");
        husband.setPassporOffice(passporOffice);
        husband.setStudentId("" + (20000 + id));
        husband.setAddress(address);
        husband.setUniversity(new University(2L,"NAU"));
        husband.setStudentId("HH12345");

        Adult wife = new Adult("Petrova", "Veronika", "Alekseevna",
                LocalDate.of(1998, 3, 12));
        wife.setPassportSeria("" + (2000 + id));
        wife.setPassportNumber("" + (20000+id));
        wife.setIssueDate(LocalDate.of(2018,4,5));
        PassporOffice passporOffice1 = new PassporOffice(2l, "", "");
        wife.setPassporOffice(passporOffice1);
        wife.setStudentId("" + (20000 + id));
        wife.setAddress(address);
        wife.setUniversity(new University(3l, "KNU"));
        wife.setStudentId("WW12345");

        Child child1 = new Child("Petrova", "Iryna", "Victorovna",
                LocalDate.of(2018, 6 ,29));
        child1.setCertificateNumber("" + (30000 + id));
        child1.setIssueDate(LocalDate.of(2018, 6 , 11));
        RegisterOffice registerOffice1 = new RegisterOffice(2l, "", "");
        child1.setRegisterOffice(registerOffice1);
        child1.setAddress(address);
        Child child2 = new Child("Petrova", "galyna", "Victorovna",
                LocalDate.of(2018, 6 ,29));
        child2.setCertificateNumber("" + (40000 + id));
        child2.setIssueDate(LocalDate.of(2018, 7 , 19));
        RegisterOffice registerOffice2 = new RegisterOffice(3l, "", "");
        child2.setRegisterOffice(registerOffice2);
        child2.setAddress(address);

        studentOrder.setHusband(husband);
        studentOrder.setWife(wife);
        studentOrder.addChild(child1);
        studentOrder.addChild(child2);
        return studentOrder;

    }
}
