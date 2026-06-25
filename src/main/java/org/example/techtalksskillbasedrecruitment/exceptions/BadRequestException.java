package org.example.techtalksskillbasedrecruitment.exceptions;


public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}