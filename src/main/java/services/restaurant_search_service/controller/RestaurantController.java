package services.restaurant_search_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.restaurant_search_service.service.RestaurantService;

import java.io.IOException;

@RestController
@RequestMapping(value = "/")
public class RestaurantController {

    private final RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseEntity searchRestaurant(@RequestParam String keyword) throws IOException {
        return service.searchRestaurant(keyword);
    }

}
