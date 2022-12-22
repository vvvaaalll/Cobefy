package hr.cobenco.Cobefy.exeptions;

public class UserException extends RuntimeException{
    public UserException(final String userNotFound) {
        super(userNotFound);
    }
}
