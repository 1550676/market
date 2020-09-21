package ru.zakharova.elena.market.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorWrapper {
    private int status;
    private String message;
    private Date timestamp;

    public ErrorWrapper(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
