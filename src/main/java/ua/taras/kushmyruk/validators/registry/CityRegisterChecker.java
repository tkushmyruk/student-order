package ua.taras.kushmyruk.validators.registry;

import ua.taras.kushmyruk.domain.register.CityRegisterResponse;
import ua.taras.kushmyruk.domain.Person;
import ua.taras.kushmyruk.exceptions.CityRegisterException;
import ua.taras.kushmyruk.exceptions.TransportException;

public interface CityRegisterChecker {
    CityRegisterResponse checkPerson(Person person) throws CityRegisterException, TransportException;
}
