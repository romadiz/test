package com.text.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class TextAnalist {
    private String id;
    private String mostFreqWord;
    private Double avgParagraphSize;
    private Double avgParagrahProcessingTime;
    private Long processingTime;
    private Date date;

    public String getMostFreqWord() {
        return mostFreqWord;
    }

    public void setMostFreqWord(String mostFreqWord) {
        this.mostFreqWord = mostFreqWord;
    }

    public Double getAvgParagraphSize() {
        return avgParagraphSize;
    }

    public void setAvgParagraphSize(Double avgParagraphSize) {
        this.avgParagraphSize = avgParagraphSize;
    }

    public Double getAvgParagrahProcessingTime() {
        return avgParagrahProcessingTime;
    }

    public void setAvgParagrahProcessingTime(Double avgParagrahProcessingTime) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TextAnalist(String mostFreqWord, Double avgParagraphSize, Double avgParagrahProcessingTime, Long processingTime) {
        this.mostFreqWord = mostFreqWord;
        this.avgParagraphSize = avgParagraphSize;
        this.avgParagrahProcessingTime = avgParagrahProcessingTime;
        this.processingTime = processingTime;
    }
}
