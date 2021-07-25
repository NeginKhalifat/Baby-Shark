package sample.exceptions;

public class CustomException extends Exception {
    String errorMsg;
    public CustomException(String s){

        super(s);
        errorMsg=s;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
