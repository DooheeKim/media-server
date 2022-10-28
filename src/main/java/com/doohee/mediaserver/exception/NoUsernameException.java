package com.doohee.mediaserver.exception;

public class NoUsernameException extends RuntimeException{
    public NoUsernameException(){
        super();
    }
    public NoUsernameException(String message){
        super(message);
    }
    public NoUsernameException(Throwable cause){
        super(cause);
    }
    public NoUsernameException(String message, Throwable cause){
        super(message,cause);
    }
}
