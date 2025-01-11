package com.insight.backend.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Question already exist")
public class QuestionAlreadyExistsException extends RuntimeException {

}
