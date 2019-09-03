package com.unibuc.bdoo;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("No resource found");
    }
}
