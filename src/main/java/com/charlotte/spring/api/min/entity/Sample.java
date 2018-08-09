package com.charlotte.spring.api.min.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * sampleテーブル
 */
@Getter
@Setter
public class Sample implements Serializable {

    private int id;
    private int version;
    private String value;
}
