package ua.taras.kushmyruk;


import ua.taras.kushmyruk.dao.StudentOrderDaoImpl;
import ua.taras.kushmyruk.domain.*;
import ua.taras.kushmyruk.domain.children.AnswerChildren;
import ua.taras.kushmyruk.domain.register.AnswerCityRegister;
import ua.taras.kushmyruk.domain.student.AnswerStudent;
import ua.taras.kushmyruk.domain.wedding.AnswerWedding;
import ua.taras.kushmyruk.exceptions.DaoExceptions;
import ua.taras.kushmyruk.mails.MailSender;
import ua.taras.kushmyruk.validators.ChildrenValidator;
import ua.taras.kushmyruk.validators.CityRegisterValidator;
import ua.taras.kushmyruk.validators.StudentValidator;
import ua.taras.kushmyruk.validators.WeddingValidator;

import java.util.LinkedList;
import java.util.List;

public class StudentOrderValidator {
    private CityRegisterValidator cityRegisterValidator;
    private WeddingValidator weddingValidator;
    private ChildrenValidator childrenValidator;
    private StudentValidator studentValidator;
    private MailSender mailSender;

    public StudentOrderValidator(){
        cityRegisterValidator = new CityRegisterValidator();
        weddingValidator = new WeddingValidator();
        childrenValidator = new ChildrenValidator();
        studentValidator = new StudentValidator();
        mailSender = new MailSender();
    }


    public static void main(String[] args) {
        StudentOrderValidator studentOrderValidator = new StudentOrderValidator();
        studentOrderValidator.checkAll();

    }

    public  void checkAll(){
           List<StudentOrder> studentOrders = readStudentOrders();
            for (StudentOrder studentOrder  : studentOrders){
                checkOneOder(studentOrder);
            }

    }
    public void checkOneOder(StudentOrder so){
        AnswerCityRegister cityRegister = checkCityRegister(so);
//        AnswerStudent answerStudent = checkStudent(so);
//        AnswerChildren answerChildren = checkChildren(so);
//        AnswerWedding answerWedding = checkWedding(so);
//
//        sendMail(so);
   }

    public static List<StudentOrder> readStudentOrders(){
        List<StudentOrder>studentOrders = null;
        try {
            studentOrders = new StudentOrderDaoImpl().getStudentOrders();
        } catch (DaoExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }


        return studentOrders;

    }

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder){
        return cityRegisterValidator.checkCityRegister(studentOrder) ;
    }

    public AnswerWedding checkWedding(StudentOrder studentOrder){
        return   weddingValidator.checkWedding(studentOrder);
    }

    public AnswerChildren checkChildren(StudentOrder studentOrder){
       return childrenValidator.checkChildren(studentOrder);
    }

    public AnswerStudent checkStudent(StudentOrder studentOrder){
      return studentValidator.checkStudent(studentOrder);
    }
    public  void sendMail(StudentOrder studentOrder){
        mailSender.sendMail(studentOrder);
    }
}
