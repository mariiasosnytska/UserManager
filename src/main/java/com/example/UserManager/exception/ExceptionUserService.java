package com.example.UserManager.exception;

public class ExceptionUserService extends Exception{
    public ExceptionUserService(String message){
        super(message);
    }

    public static String ThisUserDoesNotExist(){
        return "Sorry, this User does not exist.";
    }

    public static String NoUsersFound(){
        return "Sorry, no Users found.";
    }


}
