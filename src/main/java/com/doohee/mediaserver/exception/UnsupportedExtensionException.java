package com.doohee.mediaserver.exception;

public class UnsupportedExtensionException extends RuntimeException{
    public UnsupportedExtensionException(){
        super();
    }
    public UnsupportedExtensionException(String message){
        super(message);
    }
    public UnsupportedExtensionException(Throwable cause){
        super(cause);
    }
    public UnsupportedExtensionException(String message, Throwable cause){
        super(message, cause);
    }
}
