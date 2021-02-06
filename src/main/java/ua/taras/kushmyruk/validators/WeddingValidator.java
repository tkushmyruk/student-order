package ua.taras.kushmyruk.validators;

import ua.taras.kushmyruk.domain.wedding.AnswerWedding;
import ua.taras.kushmyruk.domain.StudentOrder;

public class WeddingValidator {
    public String hostName;
    public  String weddingHouse;
    public AnswerWedding checkWedding(StudentOrder studentOrder){
        System.out.println("Wedding running " + hostName);
        System.out.println("Where wedding was - " + weddingHouse);
        AnswerWedding answerWedding = new AnswerWedding();
        return answerWedding;
    }
}
