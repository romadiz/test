package com.text.demo.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.text.demo.model.ParagraphAnalyst;
import com.text.demo.model.TextAnalist;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class TextService {


    public ParagraphAnalyst getTextAnalyst(Integer p_start, Integer p_end, Integer w_count_min, Integer w_count_max) {
        URL url = null;
        JsonObject obj= null;
        try {
            url = new URL("http://www.randomtext.me/api/giberish/p-50/1-25");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output = "";
            String aux;
            System.out.println("Output from Server .... \n");
            while ((aux = br.readLine()) != null) {
                output = output + aux;
            }
            System.out.println(output);
            obj = new JsonParser().parse(output).getAsJsonObject();

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return processText(obj);
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
        String word = getMostFrequenceWord(words);
        Long endTime = System.currentTimeMillis();
        ParagraphAnalyst resul = new ParagraphAnalyst(word, textLenght/amount.getAsDouble(),endTime-initTime);
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


}
