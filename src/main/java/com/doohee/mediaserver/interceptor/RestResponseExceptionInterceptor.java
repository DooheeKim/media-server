package com.doohee.mediaserver.interceptor;

import com.doohee.mediaserver.dto.ErrorDto;
import com.doohee.mediaserver.exception.DuplicateMemberException;
import com.doohee.mediaserver.exception.NoUsernameException;
import com.doohee.mediaserver.exception.StorageException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class RestResponseExceptionInterceptor extends ResponseEntityExceptionHandler {
    @ResponseStatus(CONFLICT)
//    @ExceptionHandler(value={DuplicateMemberException.class, StorageException.class})
    @ResponseBody
    protected ErrorDto badRequest(RuntimeException ex, WebRequest request){
        return new ErrorDto(CONFLICT.value(), ex.getMessage());
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value={NoUsernameException.class})
    @ResponseBody
    protected ErrorDto unauthorizedRequest(RuntimeException ex, WebRequest request) {
        return new ErrorDto(UNAUTHORIZED.value(), ex.getMessage());
    }

}
