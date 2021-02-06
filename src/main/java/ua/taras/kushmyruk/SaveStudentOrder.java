package ua.taras.kushmyruk;

import ua.taras.kushmyruk.dao.DictionaryDaoImpl;
import ua.taras.kushmyruk.dao.StudentOrderDaoImpl;
import ua.taras.kushmyruk.domain.*;

import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrder {

    public static void main(String[] args)throws Exception {

        List<StudentOrder> studentOrders = new StudentOrderDaoImpl().getStudentOrders();
        System.out.println(studentOrders);
        for (StudentOrder studentOrder : studentOrders){
            System.out.println(studentOrder.getMarriageCertificatedId());
            System.out.println(studentOrder.getWife().getGivenName());
        }

    }

    static long saveStudentOrder( StudentOrder studentOrder) {
        long answer = 199;
        System.out.println();
        return answer;
    }


    static void printStudentOrder(StudentOrder studentOrder){
        System.out.println(studentOrder.getStudentOrderId());

    }
}
