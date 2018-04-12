package com.text.demo.model;

import java.util.Map;

public class ParagraphAnalyst {

    private Map<String,Integer> words;
    private Double length;
    private Long time;

    public Map<String,Integer> getWords() {
        return words;
    }

    public void setWords(Map<String,Integer> words) {
        this.words = words;
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

    public ParagraphAnalyst(Map<String,Integer> words, Double length, Long time) {
        this.words = words;
        this.length = length;
        this.time = time;
    }
}
