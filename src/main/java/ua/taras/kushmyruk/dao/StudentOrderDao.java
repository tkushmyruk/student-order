package ua.taras.kushmyruk.dao;

import ua.taras.kushmyruk.domain.StudentOrder;
import ua.taras.kushmyruk.exceptions.DaoExceptions;

import java.util.List;

public interface StudentOrderDao {
    long saveStudentOrder(StudentOrder studentOrder) throws DaoExceptions;

    List<StudentOrder> getStudentOrders() throws DaoExceptions;

}
