package com.example.demo.core.exception;

public class EntityNotExistException extends RuntimeException {
    public EntityNotExistException(Object id) {
        super("Entity with id: \"" + id.toString() + "\" could not be found.");
    }
}
