package com.example.UserManager.exception;

public class ExceptionUserService extends Exception{
    public ExceptionUserService(String message){
        super(message);
    }

    public static String ThisUserDoesNotExist(){
        return "{exception.UserNotFound}";
    }

    public static String NoUsersFound(){
        return "{exception.noUsersFound}}";
    }

}
