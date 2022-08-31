package com.devfabio.dscatalog.services.exceptions;

public class EntityNotFoundException extends RuntimeException {
    private static final long SerialVersionUID= 1L;

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
