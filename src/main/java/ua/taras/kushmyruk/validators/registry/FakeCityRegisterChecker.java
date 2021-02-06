package ua.taras.kushmyruk.validators.registry;

import ua.taras.kushmyruk.domain.Adult;
import ua.taras.kushmyruk.domain.Child;
import ua.taras.kushmyruk.domain.register.CityRegisterResponse;
import ua.taras.kushmyruk.domain.Person;
import ua.taras.kushmyruk.exceptions.CityRegisterException;
import ua.taras.kushmyruk.exceptions.TransportException;

public class FakeCityRegisterChecker  implements CityRegisterChecker {
    private static final String GOOD_1 = "1000";
    private static final String GOOD_2 = "2000";
    private static final String BAD_1 = "1001";
    private static final String BAD_2 = "2001";
    private static final String ERROR_1 = "1002";
    private static final String ERROR_2 = "2002";
    private static final String ERROR_T_1 = "1003";
    private static final String ERROR_T_2 = "2003";
    public CityRegisterResponse checkPerson(Person person) throws CityRegisterException, TransportException {
        CityRegisterResponse res = new CityRegisterResponse();
        if(person instanceof Adult){
            Adult t = (Adult) person;
            if(t.getPassportSeria().equals(GOOD_1) || t.getPassportSeria().equals(GOOD_2)){
            res.setExisting(true);
            res.setTemporal(false);
            }
            if(t.getPassportSeria().equals(BAD_1) || t.getPassportSeria().equals(BAD_2)){
                res.setExisting(false);
                res.setTemporal(false);
            }
            if(t.getPassportSeria().equals(ERROR_1) || t.getPassportSeria().equals(ERROR_2)){
                CityRegisterException ex = new CityRegisterException("Fake ERROR" + t.getPassportSeria(), "1");
                throw ex;
            }
            if(t.getPassportSeria().equals(ERROR_T_1) || t.getPassportSeria().equals(ERROR_T_2)){
                TransportException exception = new TransportException("transport Error" + t.getPassportSeria());
                throw  exception;
            }

        }
        if (person instanceof Child){
            res.setExisting(true);
            res.setTemporal(true);
        }
        System.out.println(res.toString());

        return res;
    }

}
