package services.restaurant_search_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import services.restaurant_search_service.controller.RestaurantController;
import services.restaurant_search_service.service.RestaurantService;

import java.util.Collections;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackageClasses = {RestaurantController.class, RestaurantService.class})
public class RestaurantConfiguration {

    @Value("${user-key}")
    private String user_key;

    @Bean
    public HttpHeaders httpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("user-key", user_key);
        return headers;
    }

    @Bean
    public HttpEntity httpEntity(HttpHeaders headers){
        return new HttpEntity<>("parameters", headers);
    }

}
