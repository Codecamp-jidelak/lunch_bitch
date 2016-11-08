package services.restaurant_search_service;


import model.DataModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

public interface IRestaurantSearch {

    /**
     * Search for restaurants by keyword
     * @param keyword to search
     * @return Model
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    DataModel searchRestaurant(String keyword) throws IOException;

}
