package com.IL.currencies.service;

import com.IL.currencies.repository.CurrencyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;

@Slf4j
@Service
public class APIConnection {

    @Value("${api.key}")
    private String apiKey;

    private final CurrencyRepository currencyRepository;

    APIConnection(final CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public JsonNode getDataFromServer() {
        try {
            URL url = new URL("https://api.currencyfreaks.com/latest?apikey=" + apiKey);
            return getJsonNode(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String connectToAPI(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpsResponce code " + responseCode);
        } else {
            StringBuilder actualWeatherJson = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                actualWeatherJson.append(scanner.nextLine());
            }
            scanner.close();
            return actualWeatherJson.toString();
        }
    }

    private JsonNode getJsonNode(final URL url) throws IOException {
        String currencyJson = connectToAPI(url);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(currencyJson);
    }



}
