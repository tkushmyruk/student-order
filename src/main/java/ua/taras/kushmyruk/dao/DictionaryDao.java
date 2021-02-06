package ua.taras.kushmyruk.dao;

import ua.taras.kushmyruk.domain.CountyArea;
import ua.taras.kushmyruk.domain.PassporOffice;
import ua.taras.kushmyruk.domain.RegisterOffice;
import ua.taras.kushmyruk.domain.Street;
import ua.taras.kushmyruk.exceptions.DaoExceptions;

import java.util.List;

public interface DictionaryDao {
    List<Street> findStreets(String pattern) throws DaoExceptions;
    List<PassporOffice> findPassportOfficie(String areaId) throws DaoExceptions;
    List<RegisterOffice> findRegisterOffice(String areaId) throws DaoExceptions;
    List<CountyArea> findAreas(String areaId) throws DaoExceptions;
}
