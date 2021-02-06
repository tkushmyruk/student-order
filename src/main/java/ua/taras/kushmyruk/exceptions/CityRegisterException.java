package ua.taras.kushmyruk.exceptions;

public class CityRegisterException extends Exception {
    String code;


    public CityRegisterException(String message, String code) {
        super(message);
        this.code = code;
    }

    public CityRegisterException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
