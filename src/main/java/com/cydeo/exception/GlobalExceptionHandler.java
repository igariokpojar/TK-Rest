package com.cydeo.exception;

import com.cydeo.annotation.DefaultExceptionMessage;
import com.cydeo.dto.DefaultExceptionMessageDto;

import com.cydeo.dto.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.Optional;

/*
    Whenever exception happens in your application, this class will execute it.
Okay, When our exception happens, this class will execute it.
And this is the Lp. A responsibility. But right now, what's happening in this, in this class? Why developer created some class
like this global exception. Please look at right now.
There is one method
call service, exception.
an exception. Handler says: ticketing project exception class.
You know what it means. It means this.
whenever
exception happens-> Named ticketing project exception, which is troll new ticketing project, right?
 We say, whenever any exception happens, belongs to this class ticketing project exception.
 This is right. Now. My own custom class, right? I created
again, I will repeat, whenever exception happens, belongs to ticketing project, exception.
     */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
    in my application, If any exception happens, belongs to ticketing project exception class
basically show this output.
     */

    @ExceptionHandler(TicketingProjectException.class)
    public ResponseEntity<ResponseWrapper> serviceException(TicketingProjectException se){
        String message = se.getMessage();
        return new ResponseEntity<>(ResponseWrapper.builder().success(false).code(HttpStatus.CONFLICT.value()).message(message).build(),HttpStatus.CONFLICT);
    }

/*
if any exception happens, belongs to access, deny exception class, which is the example. Here, look access to my exception.
 I don't want to see this kind of Jason what I want to see. I want to see this output
 */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseWrapper> accessDeniedException(AccessDeniedException se){
        String message = se.getMessage();
        return new ResponseEntity<>(ResponseWrapper.builder().success(false).code(HttpStatus.FORBIDDEN.value()).message(message).build(),HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler({Exception    .class, RuntimeException.class, Throwable.class, BadCredentialsException.class})
    public ResponseEntity<ResponseWrapper> genericException(Throwable e, HandlerMethod handlerMethod) {
        /*
       this method will be executed when exception belongs to exception. That class runtime exception,
        that class to global dot class. But credential exception class, if any exception happens, belongs to these classes, but not
ticketing project exception class access to my exception class. This method will execute it.
         */

        Optional<DefaultExceptionMessageDto> defaultMessage = getMessageFromAnnotation(handlerMethod.getMethod());
        if (defaultMessage.isPresent() && !ObjectUtils.isEmpty(defaultMessage.get().getMessage())) {
            ResponseWrapper response = ResponseWrapper
                    .builder()
                    .success(false)
                    .message(defaultMessage.get().getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ResponseWrapper.builder().success(false).message("Action failed: An error occurred!").code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private Optional<DefaultExceptionMessageDto> getMessageFromAnnotation(Method method) {
        DefaultExceptionMessage defaultExceptionMessage = method.getAnnotation(DefaultExceptionMessage.class);
        if (defaultExceptionMessage != null) {
            DefaultExceptionMessageDto defaultExceptionMessageDto = DefaultExceptionMessageDto
                    .builder()
                    .message(defaultExceptionMessage.defaultMessage())
                    .build();
            return Optional.of(defaultExceptionMessageDto);
        }
        return Optional.empty();
    }
}