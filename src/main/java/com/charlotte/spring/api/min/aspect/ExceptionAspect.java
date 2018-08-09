package com.charlotte.spring.api.min.aspect;

import com.charlotte.spring.api.min.dto.ErrorDto;
import com.charlotte.spring.api.min.exception.DataNotFoundException;
import com.charlotte.spring.api.min.exception.ExclusiveException;
import com.charlotte.spring.api.min.exception.UpdateFailedException;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;

@ControllerAdvice
public class ExceptionAspect {

    Logger LOGGER = LoggerFactory.getLogger(ExceptionAspect.class);

    private static final String NOT_FOUND_MESSAGE = "api is exists. but not found data.";
    private static final String EXCLUSIVE_MESSAGE = "target data is already update(or created) by other.";
    private static final String UPDATE_FAILED_MESSAGE = "sorry. update is failed by unknown cause. request data has not been updated.";
    private static final String UNEXPECTED_ERROR_MESSAGE = "sorry. failed by unknown cause. data has not been updated if you requested update(or create) data.";

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorDto> dataNotFoundException(){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(NOT_FOUND_MESSAGE);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ExclusiveException.class)
    public ResponseEntity<ErrorDto> exclusiveException(){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(EXCLUSIVE_MESSAGE);
        return new ResponseEntity<>(errorDto, HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(UpdateFailedException.class)
    public ResponseEntity<ErrorDto> updateFailedException(UpdateFailedException e){
        LOGGER.error("SYS_ERROR", e);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(UPDATE_FAILED_MESSAGE);
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ErrorDto> servletException(){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDto> throwable(Throwable t){
        LOGGER.error("SYS_ERROR", t);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(UNEXPECTED_ERROR_MESSAGE);
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
