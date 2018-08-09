package com.charlotte.spring.api.min.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ErrorDto implements Serializable {

    /** Error Message For Client */
    private String message;
}
