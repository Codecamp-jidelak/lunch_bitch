package services.restaurant_search_service.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class RestaurantService {

    private final RestTemplate restTemplate;

    private final HttpEntity<String> entity;

    @Value("${search.url}")
    private String url;

    @Autowired
    public RestaurantService(HttpEntity<String> entity, RestTemplate restTemplate) {
        this.entity = entity;
        this.restTemplate = restTemplate;
    }

    public @ResponseBody
    ResponseEntity searchRestaurant(String keyword) throws IOException {
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class, keyword);
    }

}