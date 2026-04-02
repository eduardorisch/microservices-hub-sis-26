package com.github.eduardorisch.ms_pedidos.exceptions;

public class DatabaseException extends RuntimeException{

    public DatabaseException(String message) {
        super(message);
    }
}
