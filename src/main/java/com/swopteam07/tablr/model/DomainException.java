package com.swopteam07.tablr.model;

/**
 * Exception thrown when something happens that isn't allowed in the domain model.
 */
public class DomainException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public DomainException() {
        super();
    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Exception e) {
        super(message, e);
    }

}