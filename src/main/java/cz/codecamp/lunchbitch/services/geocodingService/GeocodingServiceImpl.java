package cz.codecamp.lunchbitch.services.geocodingService;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.codecamp.lunchbitch.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class GeocodingServiceImpl implements GeocodingService{

    private final RestTemplate restTemplate;

    private final HttpEntity<String> httpEntity;

    @Value("${geocode.url}")
    private String url;

    @Value("${google-key}")
    private String key;

    @Autowired
    public GeocodingServiceImpl(@Qualifier("google") HttpEntity<String> httpEntity, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.httpEntity = httpEntity;
    }

    @Override
    public @ResponseBody
    Location getCoordinates(String address) throws IOException {
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class, address, key);
        return createLocation(responseEntity);
    }

    private Location createLocation(ResponseEntity responseEntity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Location location = new Location();
        JsonNode results = mapper.readTree(responseEntity.getBody().toString())
                .path("results");

        if(results.isArray()){

            for(JsonNode result: results){

                JsonNode locationNode = result.path("geometry").path("location");

                location
                        .setLatitude(locationNode.path("lat").toString())
                        .setLongitude(locationNode.path("lng").toString());
            }
        }

        return location;
    }
}
