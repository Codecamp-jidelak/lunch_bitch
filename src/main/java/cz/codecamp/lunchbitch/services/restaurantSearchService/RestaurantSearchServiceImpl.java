package cz.codecamp.lunchbitch.services.restaurantSearchService;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.codecamp.lunchbitch.models.DataModel;
import cz.codecamp.lunchbitch.models.Location;
import cz.codecamp.lunchbitch.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

@Service
public class RestaurantSearchServiceImpl implements RestaurantSearchService{

    private final RestTemplate restTemplate;

    private final HttpEntity<String> entity;

    @Value("${search.url}")
    private String url;

    @Autowired
    public RestaurantSearchServiceImpl(HttpEntity<String> entity, RestTemplate restTemplate) {
        this.entity = entity;
        this.restTemplate = restTemplate;
    }

    public DataModel searchForRestaurants(String keyword) throws IOException {
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class, keyword);
        return createDataModel(responseEntity);
    }

    /**
     * Map JSON response to DataModel object
     * @return DataModel object
     * @throws IOException from reading JSON
     */
    private DataModel createDataModel(ResponseEntity responseEntity) throws IOException {

        DataModel dataModel = new DataModel();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode restaurantsNode = objectMapper.readTree(responseEntity.getBody().toString()).path("restaurants");

        if (restaurantsNode.isArray()) {

            for (JsonNode jsonNode : restaurantsNode) {

                Restaurant restaurant = new Restaurant();

                JsonNode restaurantNode = jsonNode.path("restaurant");

                restaurant
                        .setRes_id(restaurantNode.path("id").textValue())
                        .setName(restaurantNode.path("name").textValue());

                JsonNode location = restaurantNode.path("location");

                restaurant.setLocation(new Location()
                        .setAddress(location.path("address").asText())
                        .setCity(location.path("city").textValue())
                        .setCountry_id(location.path("country_id").textValue())
                        .setLatitude(location.path("latitude").textValue())
                        .setLongitude(location.path("longitude").textValue())
                        .setLocality(location.path("locality").textValue())
                        .setZipcode(location.path("zipcode").textValue()));

                dataModel.addRestaurant(restaurant);
            }

        }
        return dataModel;
    }

}