package com.revature.exception;

public class InvalidAccountLoginException extends Exception{
    public InvalidAccountLoginException(String message){
        super(message);
    }
}
