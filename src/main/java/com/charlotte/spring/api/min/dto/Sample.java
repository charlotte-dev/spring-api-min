package com.charlotte.spring.api.min.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO APIのリクエスト/レスポンスはこっちを使う
 */
@Getter
@Setter
public class Sample extends HeaderDto{
    public static class Whole extends Sample{
        private String value;
    }
}
