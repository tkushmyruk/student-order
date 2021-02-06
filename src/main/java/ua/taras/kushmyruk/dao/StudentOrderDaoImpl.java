package ua.taras.kushmyruk.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.taras.kushmyruk.config.Config;
import ua.taras.kushmyruk.domain.*;
import ua.taras.kushmyruk.exceptions.DaoExceptions;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentOrderDaoImpl implements StudentOrderDao {
    private static final Logger logger  = LoggerFactory.getLogger(StudentOrderDaoImpl.class);

    private static final String INSERT_ORDER = "INSERT INTO jc_student_order(" +
            " student_order_status, student_order_date, h_sur_name, h_given_name, h_patronymic, h_date_of_birth,\n" +
            "h_passport_seria,  h_passport_number, h_passport_date, h_passport_office_id, h_post_index, h_street_code, h_building,\n" +
            "h_extension, h_apartment,h_university_id, h_student_number, w_sur_name,  w_given_name,  w_patronymic, w_date_of_birth, w_passport_seria, w_passport_number,\n" +
            "w_passport_date, w_passport_office_id, w_post_index, w_street_code, w_building, w_extension, w_apartment, w_university_id," +
            "w_student_number, certificate_id, register_office_id, marriage_date)" +
            "VALUES(?,?,?,?," +
            "?,?,?," +
            "?,?,?,?," +
            "?,?,?,?," +
            "?,?,?,?," +
            "?,?,?,?," +
            "?,?,?,?," +
            "?,?,?,?," +
            "?,?,?,?);";

    private static final String INSERT_CHILD = "INSERT INTO jc_student_child(" +
            "student_order_id, c_sur_name, c_given_name, c_patronymic, c_date_of_birth, c_certificate_number, c_certificate_date,\n" +
            "c_register_office_id, c_post_index, c_street_code, c_building, c_extension, c_apartment)" +
            "VALUES (" +
            "?,?,?," +
            "?,?,?,?," +
            "?,?,?,?," +
            "?,?);";

    public static final String SELECT_ORDERS = "" +
            "SELECT so.*, ro.r_office_area_id, ro.r_office_name, " +
            "po_h.p_office_area_id AS h_p_office_area_id, po_h.p_office_name AS h_p_office_name," +
            "po_w.p_office_area_id AS w_p_office_area_id, po_w.p_office_name AS w_p_office_name " +
            "FROM jc_student_order so " +
            "INNER JOIN jc_register_office ro ON so.register_office_id = ro.r_office_id " +
            "INNER JOIN jc_passport_office po_h ON so.h_passport_office_id = po_h.p_office_id " +
            "INNER JOIN jc_passport_office po_w ON so.w_passport_office_id = po_w.p_office_id " +
            "WHERE student_order_status = ? ORDER BY student_order_date LIMIT ? ;";

    private static final String SELECT_CHILD = "SELECT soc.*, ro.r_office_area_id , ro.r_office_name " +
            "FROM jc_student_child soc " +
            "INNER JOIN jc_register_office ro ON ro.r_office_id = soc.c_register_office_id " +
            "WHERE soc.student_order_id IN ";

    public static final String SELECT_ORDERS_FULL = "" +
            "SELECT so.*, ro.r_office_area_id, ro.r_office_name, " +
            "po_h.p_office_area_id as h_p_office_area_id, " +
            "po_h.p_office_name as h_p_office_name, " +
            "po_w.p_office_area_id as w_p_office_area_id, " +
            "po_w.p_office_name as w_p_office_name, " +
            "soc.*, ro_c.r_office_area_id, ro_c.r_office_name " +
            "FROM jc_student_order so " +
            "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
            "INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id " +
            "INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id " +
            "INNER JOIN jc_student_child soc ON soc.student_order_id = so.student_order_id " +
            "INNER JOIN jc_register_office ro_c ON ro_c.r_office_id = soc.c_register_office_id " +
            "WHERE student_order_status = ? ORDER BY so.student_order_id LIMIT ? ";


    private Connection getConnection() throws SQLException{
        return ConnectionBuilder.getConnection();
    }
    @Override
    public long saveStudentOrder(StudentOrder studentOrder) throws DaoExceptions {
        Long result = -1l;

        logger.debug("SO:{}", studentOrder);

        try(Connection connection = getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"});
            connection.setAutoCommit(false);
       try {

                preparedStatement.setInt(1, StudentOrderStatus.START.ordinal());
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

                int start = 3;
                start = setParamsForAdult(preparedStatement, studentOrder.getHusband(), 3);
                start = setParamsForAdult(preparedStatement, studentOrder.getWife(), start);

                preparedStatement.setString(start, studentOrder.getMarriageCertificatedId());
                preparedStatement.setLong(start + 1, studentOrder.getMarriageOffice().getOfficeId());
                preparedStatement.setDate(start + 2, Date.valueOf(studentOrder.getMarriageDate()));

                preparedStatement.executeUpdate();

                ResultSet gkRs = preparedStatement.getGeneratedKeys();

                if (gkRs.next()) {
                    result = gkRs.getLong(1);
                }

                saveChildren(connection, studentOrder, result);
                connection.commit();
            }
            catch (SQLException e){
                logger.error(e.getMessage(), e);
                connection.rollback();
                throw new DaoExceptions();
          }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    private void saveChildren(Connection connection, StudentOrder studentOrder, Long soId) throws SQLException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CHILD)) {
            int count = 0;

            for (Child child : studentOrder.getChildren()){
                preparedStatement.setLong(1, soId);
                setParamsForChild (child, preparedStatement);
                preparedStatement.addBatch();
                if(count > 10){
                    preparedStatement.executeBatch();
                    count = 0;
                }
            }
                preparedStatement.executeBatch();
        }
    }

    private void setParamsForChild(Child child, PreparedStatement preparedStatement) throws SQLException {
        int start = setParamsForPerson(preparedStatement, child, 2);
        preparedStatement.setString(start, child.getCertificateNumber());
        preparedStatement.setDate(start + 1, Date.valueOf(child.getIssueDate()));
        preparedStatement.setLong(start + 2, child.getRegisterOffice().getOfficeId());
        setParamsForAddress(preparedStatement, child, start+3);
    }


    private int setParamsForAdult(PreparedStatement preparedStatement, Adult adult, int start) throws SQLException {
         start = setParamsForPerson(preparedStatement, adult, start);
        preparedStatement.setString(start, adult.getPassportSeria());
        preparedStatement.setString(start +1, adult.getPassportNumber());
        preparedStatement.setDate(start + 2, Date.valueOf(adult.getIssueDate()));
        preparedStatement.setLong(start + 3, adult.getPassportOffice().getOfficeId());

        start =  setParamsForAddress(preparedStatement, adult, start + 4);
        System.out.println(start);
        preparedStatement.setLong(start, adult.getUniversity().getUniversityID());
        preparedStatement.setString(start+1, adult.getStudentId());

        return  start + 2;
    }

    private int setParamsForAddress(PreparedStatement preparedStatement, Person person, int start) throws SQLException {
        Address address = person.getAddress();
        preparedStatement.setString(start,address.getPostCode());
        preparedStatement.setLong(start + 1, address.getStreet().getStreet_code());
        preparedStatement.setString(start + 2, address.getBuilding());
        preparedStatement.setString(start + 3, address.getExtension());
        preparedStatement.setString(start + 4, address.getApartment());
        return start + 5;
    }

    private int setParamsForPerson(PreparedStatement preparedStatement, Person person, int start) throws SQLException {
        preparedStatement.setString(start, person.getSirName());
        preparedStatement.setString(start+ 1, person.getGivenName());
        preparedStatement.setString(start + 2,person.getPatronymic());
        preparedStatement.setDate(start + 3, Date.valueOf(person.getDateOfBirth()));
        return start +4;
    }

    @Override
    public List<StudentOrder> getStudentOrders() throws DaoExceptions {
//        return getStudentOrdersTwoSelect();
       return getStudentOrderOneSelect();
    }
    private List<StudentOrder> getStudentOrderOneSelect(){
        List<StudentOrder> studentOrders  = new LinkedList<>();
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS_FULL);
            preparedStatement.setInt(1, StudentOrderStatus.START.ordinal());
            int limit = Integer.parseInt(Config.getProperty(Config.DB_LIMIT));
            preparedStatement.setInt(2, limit);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Long> ids = new LinkedList<>();
            Map<Long, StudentOrder> map = new HashMap<>();
            int count = 0;
            while (resultSet.next()){
                System.out.println("while");
                Long soId = resultSet.getLong("student_order_id");
                if(!map.containsKey(soId)) {
                    System.out.println("if block");

                    StudentOrder studentOrder = getStudentOrder(resultSet);
                    studentOrders.add(studentOrder);
                    ids.add(studentOrder.getStudentOrderId());
                    map.put(soId, studentOrder);
                }
                StudentOrder so = map.get(soId);
                Child child = fillChild(resultSet);
                so.addChild(child);
                count++;
            }
            if (count >= limit){
                studentOrders.remove(studentOrders.size() -1);
            }

            resultSet.close();

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return studentOrders;

    }

    private StudentOrder getStudentOrder(ResultSet resultSet) throws SQLException {
        StudentOrder studentOrder = new StudentOrder();
        fillStudentOrder(resultSet, studentOrder);
        fillMerraigeOrder(resultSet, studentOrder);
        Adult husbant = fillAdult(resultSet, "h_");
        Adult wife = fillAdult(resultSet, "w_");
        studentOrder.setHusband(husbant);
        studentOrder.setWife(wife);
        return studentOrder;
    }

    private List<StudentOrder> getStudentOrdersTwoSelect() {
        List<StudentOrder> studentOrders  = new LinkedList<>();
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS);
            preparedStatement.setInt(1, StudentOrderStatus.START.ordinal());
            preparedStatement.setInt(2, Integer.parseInt(Config.getProperty(Config.DB_LIMIT)));
          ResultSet resultSet = preparedStatement.executeQuery();
          List<Long> ids = new LinkedList<>();
          while (resultSet.next()){
              StudentOrder studentOrder = getStudentOrder(resultSet);
              studentOrders.add(studentOrder);
              ids.add(studentOrder.getStudentOrderId());
          }
          findChildren(connection, studentOrders);
          String s = ids.stream().map(String::valueOf).collect(Collectors.joining());
            System.out.println(s);

          resultSet.close();

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return studentOrders;
    }

    private void findChildren(Connection connection, List<StudentOrder> studentOrders) {
      String query =  "(" + studentOrders.stream()
              .map(studentOrder -> String.valueOf(studentOrder.getStudentOrderId()))
              .collect(Collectors.joining(",")) + ");";
       Map<Long, StudentOrder> studentOrderMap =  studentOrders.stream().collect(Collectors.toMap(so -> so.getStudentOrderId(), so -> so));
      try(PreparedStatement preparedStatement  =  connection.prepareStatement(SELECT_CHILD + query)){
          ResultSet resultSet = preparedStatement.executeQuery();
          while (resultSet.next()) {
              Child child = fillChild(resultSet);
              StudentOrder studentOrder = studentOrderMap.get(resultSet.getLong("student_order_id"));
              studentOrder.addChild(child);
          }


      } catch (SQLException e) {
          logger.error(e.getMessage(), e);
          e.printStackTrace();
      }
    }

    private Child fillChild(ResultSet rs)throws SQLException {
        String surName = rs.getString("c_sur_name");
        String givenName = rs.getString("c_given_name");
        String patronymic = rs.getString("c_patronymic");
        LocalDate dateOfBirth = rs.getDate("c_date_of_birth").toLocalDate();

        Child child = new Child(surName,givenName, patronymic, dateOfBirth);

        child.setCertificateNumber(rs.getString("c_certificate_number"));
        child.setIssueDate(rs.getDate("c_certificate_date").toLocalDate());

        Long roId = rs.getLong("c_register_office_id");
        String roArea = rs.getString("r_office_area_id");
        String roName = rs.getString("r_office_name");
        RegisterOffice ro = new RegisterOffice(roId, roArea, roName);
        child.setRegisterOffice(ro);

        Address adr = new Address();
        Street st = new Street(rs.getLong("c_street_code"), "");
        adr.setStreet(st);
        adr.setPostCode(rs.getString("c_post_index"));
        adr.setBuilding(rs.getString("c_building"));
        adr.setExtension(rs.getString("c_extension"));
        adr.setApartment(rs.getString("c_apartment"));
        child.setAddress(adr);

        return child;
    }


    private Adult fillAdult(ResultSet resultSet, String pref) throws SQLException {
        Adult adult = new Adult();
        adult.setSirName(resultSet.getString(pref + "sur_name"));
        adult.setGivenName(resultSet.getString(pref+ "given_name"));
        adult.setPatronymic(resultSet.getString(pref + "patronymic"));
        adult.setDateOfBirth(resultSet.getDate(pref + "date_of_birth").toLocalDate());
        adult.setPassportSeria(resultSet.getString(pref + "passport_seria"));
        adult.setPassportNumber(resultSet.getString(pref + "passport_number"));
        adult.setIssueDate(resultSet.getDate(pref + "passport_date").toLocalDate());
        Long psId = resultSet.getLong(pref + "passport_office_id");
        String psArea = resultSet.getString(pref + "p_office_area_id");
        String psOfficeName = resultSet.getString(pref + "p_office_name");
        PassporOffice passporOffice = new PassporOffice(psId, psArea, psOfficeName);
        adult.setPassporOffice(passporOffice);
        Address address = new Address();
        Street street = new Street(resultSet.getLong(pref + "street_code"), "");
        address.setPostCode(resultSet.getString(pref + "post_index"));
        address.setBuilding(resultSet.getString(pref + "building"));
        address.setExtension(resultSet.getString(pref + "extension"));
        address.setApartment(resultSet.getString(pref + "apartment"));
        address.setStreet(street);
        adult.setAddress(address);
        University university =  new University(resultSet.getLong(pref + "university_id"), "");
        adult.setUniversity(university);
        adult.setStudentId(resultSet.getString(pref + "student_number"));

        return adult;

    }


    private void fillMerraigeOrder(ResultSet resultSet, StudentOrder studentOrder) throws SQLException {
        studentOrder.setMarriageCertificatedId(resultSet.getString("certificate_id"));
        studentOrder.setMarriageDate(resultSet.getDate("marriage_date").toLocalDate());
        RegisterOffice registerOffice = new RegisterOffice();
        registerOffice.setOfficeId(resultSet.getLong("register_office_id"));
        registerOffice.setOfficeAreaId(resultSet.getString("r_office_area_id"));
        registerOffice.setOfficeName(resultSet.getString("r_office_name"));
        studentOrder.setMarriageOffice(registerOffice);

    }

    private void fillStudentOrder(ResultSet resultSet, StudentOrder studentOrder) throws SQLException{
        studentOrder.setStudentOrderId(resultSet.getLong("student_order_id"));
        studentOrder.setStudetnOrderDate(resultSet.getTimestamp("student_order_date").toLocalDateTime());
        studentOrder.setStudentOrderStatus(StudentOrderStatus.fromValue(resultSet.getInt("student_order_status")));

    }
}
