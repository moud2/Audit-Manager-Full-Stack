package com.insight.backend.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Question already exist")
public class QuestionFoundException extends RuntimeException {

}
