package ua.taras.kushmyruk.exceptions;

public class DaoExceptions extends Exception {
    public DaoExceptions() {
    }

    public DaoExceptions(String message) {
        super(message);
    }

    public DaoExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoExceptions(Throwable cause) {
        super(cause);
    }
}
