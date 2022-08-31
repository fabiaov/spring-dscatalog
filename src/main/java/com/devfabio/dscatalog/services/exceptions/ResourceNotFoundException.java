package com.devfabio.dscatalog.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private static final long SerialVersionUID= 1L;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
