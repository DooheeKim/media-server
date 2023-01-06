package com.doohee.mediaserver.exception;

public class NoVideoIdException extends RuntimeException{
    public NoVideoIdException(){
        super();
    }
    public NoVideoIdException(String message){
        super(message);
    }
    public NoVideoIdException(Throwable cause){
        super(cause);
    }
    public NoVideoIdException(String message, Throwable cause){
        super(message,cause);
    }
}
