package ua.taras.kushmyruk.validators;

import ua.taras.kushmyruk.domain.children.AnswerChildren;
import ua.taras.kushmyruk.domain.StudentOrder;

public class ChildrenValidator {
   public String hostName;
     public String login;
    public String password;
    public AnswerChildren checkChildren(StudentOrder studentOrder){
        System.out.println("Children check is running " + hostName + ", " + login + ", " + password );
        AnswerChildren answerChildren = new AnswerChildren();
        return answerChildren;
    }
}
