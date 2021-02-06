package ua.taras.kushmyruk.validators;

import ua.taras.kushmyruk.domain.Person;
import ua.taras.kushmyruk.domain.register.AnswerCityRegister;
import ua.taras.kushmyruk.domain.Child;
import ua.taras.kushmyruk.domain.register.AnswerCityRegistryItem;
import ua.taras.kushmyruk.domain.register.CityRegisterResponse;
import ua.taras.kushmyruk.domain.StudentOrder;
import ua.taras.kushmyruk.exceptions.CityRegisterException;
import ua.taras.kushmyruk.exceptions.TransportException;
import ua.taras.kushmyruk.validators.registry.FakeCityRegisterChecker;
import ua.taras.kushmyruk.validators.registry.CityRegisterChecker;

import java.util.List;

public class CityRegisterValidator {
    public String hostName;
    public int port;
     public String login;
     public String password;
     private CityRegisterChecker personChecker;
     public static final String IC_CODE = "NO_GRN";

     public CityRegisterValidator(){
         personChecker = new FakeCityRegisterChecker();
     }

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder){
         AnswerCityRegister answerCityRegister = new AnswerCityRegister();
          answerCityRegister.addItem(checkPerson(studentOrder.getHusband()));
           answerCityRegister.addItem(checkPerson(studentOrder.getWife()));

             List<Child> children = studentOrder.getChildren();
             for(Child child : children) {
                answerCityRegister.addItem(checkPerson(child));
             }

        return answerCityRegister;
    }
    public AnswerCityRegistryItem checkPerson(Person person){
         AnswerCityRegistryItem.CityStatus status;
         AnswerCityRegistryItem.CityError error = null;
         try {
             CityRegisterResponse cans = personChecker.checkPerson(person);
             status = cans.isExisting()? AnswerCityRegistryItem.CityStatus.YES : AnswerCityRegistryItem.CityStatus.NO;
         }
         catch (CityRegisterException ex){
             ex.printStackTrace(System.out);
             status= AnswerCityRegistryItem.CityStatus.ERROR;
             error = new AnswerCityRegistryItem.CityError(ex.getCode(), ex.getMessage());
         }
         catch (TransportException ex){
             ex.printStackTrace(System.out);
             status= AnswerCityRegistryItem.CityStatus.ERROR;
             error = new AnswerCityRegistryItem.CityError(IC_CODE, ex.getMessage());
         }
         catch (Exception ex){
             ex.printStackTrace(System.out);
             status= AnswerCityRegistryItem.CityStatus.ERROR;
             error = new AnswerCityRegistryItem.CityError(IC_CODE, ex.getMessage());
         }
         AnswerCityRegistryItem answerCityRegistryItem = new AnswerCityRegistryItem(person, status, error);
         return answerCityRegistryItem;
    }
}
