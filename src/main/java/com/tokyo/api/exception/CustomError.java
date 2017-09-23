package com.tokyo.api.exception;

public class CustomError {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public CustomError(){
    }

    public CustomError(String error){
        this.error = error;
    }

}
