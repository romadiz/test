package com.text.demo.model;

public class TextAnalist {

    private String mostFreqWord;
    private Integer avgParagraphSize;
    private Long avgParagrahProcessingTime;
    private Long processingTime;

    public String getMostFreqWord() {
        return mostFreqWord;
    }

    public void setMostFreqWord(String mostFreqWord) {
        this.mostFreqWord = mostFreqWord;
    }

    public Integer getAvgParagraphSize() {
        return avgParagraphSize;
    }

    public void setAvgParagraphSize(Integer avgParagraphSize) {
        this.avgParagraphSize = avgParagraphSize;
    }

    public Long getAvgParagrahProcessingTime() {
        return avgParagrahProcessingTime;
    }

    public void setAvgParagrahProcessingTime(Long avgParagrahProcessingTime) {
        this.avgParagrahProcessingTime = avgParagrahProcessingTime;
    }

    public Long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(Long processingTime) {
        this.processingTime = processingTime;
    }

    public TextAnalist() {
    }

    public TextAnalist(String mostFreqWord, Integer avgParagraphSize, Long avgParagrahProcessingTime, Long processingTime) {
        this.mostFreqWord = mostFreqWord;
        this.avgParagraphSize = avgParagraphSize;
        this.avgParagrahProcessingTime = avgParagrahProcessingTime;
        this.processingTime = processingTime;
    }
}
