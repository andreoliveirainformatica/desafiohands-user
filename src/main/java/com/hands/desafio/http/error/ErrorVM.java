package com.hands.desafio.http.error;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class ErrorVM implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final String description;
    private List<FieldErrorVM> fieldErrors;

    public ErrorVM(String message) {
        this(message, null);
    }

    public ErrorVM(String message, String description) {
        this.message = message;
        this.description = description;
    }

    public void add(String objectName, String field, String message) {
        fieldErrors.add(new FieldErrorVM(objectName, field, message));
    }
}
