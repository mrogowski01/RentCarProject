package com.example.zti_project.exceptions;

public class RegisterException  extends RuntimeException {
    public RegisterException() {
        super("Couldn't register new user!");
    }
}
