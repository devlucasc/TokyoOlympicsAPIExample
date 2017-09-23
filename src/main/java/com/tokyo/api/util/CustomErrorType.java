package com.tokyo.api.util;


public class CustomErrorType extends Throwable {

    private final String errorMessage;

    public CustomErrorType(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
