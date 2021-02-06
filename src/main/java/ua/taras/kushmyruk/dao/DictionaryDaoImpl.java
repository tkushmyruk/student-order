package ua.taras.kushmyruk.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.taras.kushmyruk.domain.CountyArea;
import ua.taras.kushmyruk.domain.PassporOffice;
import ua.taras.kushmyruk.domain.RegisterOffice;
import ua.taras.kushmyruk.domain.Street;
import ua.taras.kushmyruk.exceptions.DaoExceptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDaoImpl implements DictionaryDao {
   private  static final java.lang.String GET_STREET = "SELECT street_code, street_name FROM jc_street" +
           " WHERE UPPER( street_name) LIKE UPPER (?)";
   private static final String GET_OFFICE = "SELECT * FROM jc_passport_office WHERE p_office_area_id = ?";
   private static final String GET_REGISTER = "SELECT * FROM jc_register_office WHERE r_office_area_id = ?";
   private static final String GET_AREAS = "SELECT * FROM jc_country_struct WHERE area_id  LIKE ? AND area_id <> ?";

   private static final Logger logger  = LoggerFactory.getLogger(DictionaryDaoImpl.class);


    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();

    }
    @Override
    public List<Street> findStreets(String pattern) throws DaoExceptions {
        List<Street> result = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_STREET)) {
            statement.setString(1, "%" + pattern +"%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Street street = new Street(resultSet.getLong("street_code"), resultSet.getString("street_name"));
                result.add(street);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<PassporOffice> findPassportOfficie(String areaId) throws DaoExceptions {
        List<PassporOffice> result = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_OFFICE)) {
            statement.setString(1,  areaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                PassporOffice passporOffice = new PassporOffice(resultSet.getLong("p_office_id"),
                        resultSet.getString("p_office_area_id"),
                        resultSet.getString("p_office_name"));
                result.add(passporOffice);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<RegisterOffice> findRegisterOffice(String areaId) throws DaoExceptions {
        List<RegisterOffice> result = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_REGISTER)) {
            statement.setString(1,  areaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                RegisterOffice registerOffice = new RegisterOffice(resultSet.getLong("r_office_id"),
                        resultSet.getString("r_office_area_id"),
                        resultSet.getString("r_office_name"));
                result.add(registerOffice);

            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<CountyArea> findAreas(String areaId) throws DaoExceptions {
        List<CountyArea> result = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_AREAS)) {
            String param1 = buildParam(areaId);
            String param2 = areaId;
            statement.setString(1,  param1);
            statement.setString(2, param2);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CountyArea countyArea = new CountyArea(resultSet.getString("area_id"),
                        resultSet.getString("area_name"));
                result.add(countyArea);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);

        }
        return result;
    }

    private String buildParam(String areaId) throws SQLException{
        if (areaId == null || areaId.trim().isEmpty()){
            return "__0000000000";
        }
        if(areaId.endsWith("0000000000")){
           return areaId.substring(0, 2) + "___0000000";
        }
        else if(areaId.endsWith("0000000")){
            return areaId.substring(0,5) + "___0000";
        }
        else if(areaId.endsWith("0000")){
            return areaId.substring(0, 8) + "____";
        }
        throw new SQLException("Invalid Parameter 'area_id' :" + areaId );
    }
}
