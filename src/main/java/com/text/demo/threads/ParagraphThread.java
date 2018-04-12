package com.text.demo.threads;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.text.demo.model.ParagraphAnalyst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ParagraphThread implements Runnable{

    private ParagraphAnalyst paragraphAnalyst;

    private URL url;

    private JsonObject obj;

    public ParagraphAnalyst getParagraphAnalyst() {
        return paragraphAnalyst;
    }

    public void setParagraphAnalyst(ParagraphAnalyst paragraphAnalyst) {
        this.paragraphAnalyst = paragraphAnalyst;
    }

    public JsonObject getObj() {
        return obj;
    }

    public void setObj(JsonObject obj) {
        this.obj = obj;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public void run() {
        Long initTime = System.currentTimeMillis();
        getJSON();
        geerateParagraphAnalyst(initTime);
    }

    private void getJSON(){
        try{
            HttpURLConnection conn = (HttpURLConnection) this.url.openConnection();
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
            this.obj = new JsonParser().parse(output).getAsJsonObject();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void geerateParagraphAnalyst(Long initTime){
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
        this.paragraphAnalyst = new ParagraphAnalyst(words, textLenght/amount.getAsDouble(),endTime-initTime);
    }
}
