package cz.codecamp.lunchbitch.configuration.restaurantSearchConfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

@org.springframework.context.annotation.Configuration
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
