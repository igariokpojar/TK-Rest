package com.cydeo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DefaultExceptionMessageDto {
/*
I'm going to put this
annotation top of the method. And this method is going to throw that exception.
my annotation exception, whatever what is going to basically throw whatever message you give default message is what
default messages? Nothing. Basically if you want to use the default, you see, is nothing.
But if you want to modify this one, if you want to customize this message, then which message is going to be thrown
 */
    private String message;
}