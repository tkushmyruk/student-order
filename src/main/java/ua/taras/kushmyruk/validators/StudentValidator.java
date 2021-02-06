package ua.taras.kushmyruk.validators;

import ua.taras.kushmyruk.domain.student.AnswerStudent;
import ua.taras.kushmyruk.domain.StudentOrder;

public class StudentValidator {
    public String hostName;
    public int howManyVodkaDrunk;
    public AnswerStudent checkStudent(StudentOrder studentOrder){
        System.out.println("students are checking " + hostName);
        System.out.println("circumstances of expelling - " + howManyVodkaDrunk + "l of vodka was drunk" );
        AnswerStudent answerStudent = new AnswerStudent();
        return answerStudent;
    }
}
