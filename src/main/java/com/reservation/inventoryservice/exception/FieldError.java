package com.reservation.inventoryservice.exception;

import lombok.Data;

@Data
public class FieldError {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;

}
