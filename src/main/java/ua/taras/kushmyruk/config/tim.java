package ua.taras.kushmyruk.config;

public class tim {
    public static void main(String[] args) {
        String correct = "soc.с_register_office_id";
        String norrect = "soc.c_register_office_id";
        char[] correc = correct.toCharArray();
        char[] norrec = norrect.toCharArray();

        for (int i = 0; i <correc.length ; i++) {
            if(correc[i] != norrec[i]){
                System.out.println(correc[i] +" - " + norrec[i] + " " + i);
            }

        }
    }
}
