package com.text.demo.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.client.MongoDatabase;
import com.text.demo.model.ParagraphAnalyst;
import com.text.demo.model.TextAnalist;

import com.text.demo.repository.TextAnalysRepository;
import com.text.demo.threads.ParagraphThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Service
public class TextService {

    private static final String API_REST_URL = "http://www.randomtext.me/api/giberish/p-(P)/(COUNT_MIN)-(COUNT_MAX)";

    private static final Logger log = Logger.getLogger(TextService.class.getName());

    @Autowired
    private TextAnalysRepository textAnalysRepository;

    public TextAnalist getTextAnalyst(Integer p_start, Integer p_end, Integer w_count_min, Integer w_count_max) {
        Long initTime = System.currentTimeMillis();
        JsonObject obj= null;
        List<URL> urls = getURLs(p_start, p_end, w_count_min, w_count_max);
        List<Runnable> process = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        urls.forEach(url ->{
            ParagraphThread paragraphThread = new ParagraphThread();
            paragraphThread.setUrl(url);
            process.add(paragraphThread);
            Thread t = new Thread(paragraphThread);
            threads.add(t);
            t.start();
        });
        Boolean finish = false;
        while(!finish){
            threads.stream();

            for(Thread thread:threads){
                if(thread.isAlive()){
                    finish = false;
                    break;
                }
                finish = true;
            }
        }
        TextAnalist resul = getResults(process);
        Long endTime = System.currentTimeMillis();
        resul.setProcessingTime(endTime-initTime);
        resul.setDate(new Date());
        resul = textAnalysRepository.save(resul);
        return resul;
    }

    private TextAnalist getResults(List<Runnable> process) {
        TextAnalist resul = new TextAnalist();
        String word = "";
        Map<String, Integer> words = new HashMap<>();
        Double avgParagraphSize = 0D;
        Double avgParagrahProcessingTime = 0D;
        Long processingTime = 0L;
        for(Runnable proces:process){
            ParagraphThread thread = (ParagraphThread) proces;
            Set<String> keys = thread.getParagraphAnalyst().getWords().keySet();
            for(String key:keys){
                words.merge(key.toLowerCase(), 1, Integer::sum);
            }
            avgParagrahProcessingTime += thread.getParagraphAnalyst().getTime();
            avgParagraphSize += thread.getParagraphAnalyst().getLength();
        }
        avgParagrahProcessingTime = avgParagrahProcessingTime / process.size();
        resul.setAvgParagrahProcessingTime(avgParagrahProcessingTime);
        avgParagraphSize = avgParagraphSize / process.size();
        resul.setAvgParagraphSize(avgParagraphSize);
        word = getMostFrequenceWord(words);
        resul.setMostFreqWord(word);
        return resul;
    }

    /**
     *
     * @param obj
     * @return
     */
    private ParagraphAnalyst processText(JsonObject obj) {
        Long initTime = System.currentTimeMillis();
        Map<String, Integer> words = new HashMap<>();
        JsonElement paragraph = obj.get("text_out");
        String textOut = paragraph.getAsString();
        String[] texts = textOut.split("\r");
        Integer textLenght = 0;
        for(int i = 0; i<texts.length; i++){
            String text = texts[i];
            // Clean text
            text = text.replace("<p>","");
            text = text.replace("</p>","");
            text = text.replace(".","");
            textLenght = textLenght + text.length();
            String[] wordsText = text.split(" ");
            for(int w = 0; w<wordsText.length; w++){
                words.merge(wordsText[w].toLowerCase(), 1, Integer::sum);
            }
        }
        JsonElement amount = obj.get("amount");
        System.out.println(textLenght);
        System.out.println(textLenght/amount.getAsDouble());
        Long endTime = System.currentTimeMillis();
        ParagraphAnalyst resul = new ParagraphAnalyst(words, textLenght/amount.getAsDouble(),endTime-initTime);
        return resul;
    }

    private List<URL> getURLs(Integer p_start, Integer p_end, Integer w_count_min, Integer w_count_max){
        List<URL> resul = new ArrayList<>();
        for(Integer p = p_start; p <= p_end; p++){
            String sURL = API_REST_URL;
            sURL = sURL.replace("(P)", p.toString());
            sURL = sURL.replace("(COUNT_MIN)", w_count_min.toString());
            sURL = sURL.replace("(COUNT_MAX)", w_count_max.toString());
            URL url = null;
            try {
                url = new URL(sURL);
                resul.add(url);
            } catch (MalformedURLException e) {
                log.log(Level.WARNING, "Wrong URL: " + e.getMessage());
            }
        }
        return resul;
    }

    private String getMostFrequenceWord(Map<String, Integer> words){
        String resul = "";
        Integer r = 0;
        Set<String> keys = words.keySet();
        for(String key:keys){
            if(words.get(key)>r){
                resul = key;
                r = words.get(key);
            }
        }
        return resul;
    }


    public List<TextAnalist> getHistory() {
        List<TextAnalist> textAnalists = textAnalysRepository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"date")));
        return textAnalists.subList(0,10);
    }
}
