package com.springexp.correlation.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class WeatherController {

    @Value("${api.weather.host}")
    String weatherAPIHost;

    @Value("${api.weather.appid}")
    String weatherAPIAppId;

    @RequestMapping("/today")
    public String getWeatherToday(@RequestParam(value = "city") String city) {
        RestTemplate restTemplate = new RestTemplate();

        String getWeatherByCityUrl = weatherAPIHost + "?q={city}&appid={appId}";

        ResponseEntity<Map> response = restTemplate.getForEntity(getWeatherByCityUrl, Map.class, city, weatherAPIAppId);

        log.info("Weather in {} response : {} ", city, response.getBody());

        String weatherDescription = (String) ((Map) ((List) response.getBody().get("weather")).get(0)).get("description");

        return "Weather today is " + weatherDescription + "\n";
    }

}

