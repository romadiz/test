package com.text.demo.model;

import java.util.Map;

public class ParagraphAnalyst {

    private String word;
    private Double length;
    private Long time;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public ParagraphAnalyst(String word, Double length, Long time) {
        this.word = word;
        this.length = length;
        this.time = time;
    }
}
