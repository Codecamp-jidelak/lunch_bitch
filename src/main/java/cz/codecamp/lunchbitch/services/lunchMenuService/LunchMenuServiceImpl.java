package cz.codecamp.lunchbitch.services.lunchMenuService;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.codecamp.lunchbitch.models.Dish;
import cz.codecamp.lunchbitch.models.LunchMenu;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.services.mergerService.MergerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class LunchMenuServiceImpl implements LunchMenuService {

    private final Logger logger;

    private final RestTemplate restTemplate;

    private final HttpEntity<String> entity;

    private final MergerService mergerService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");

    @Value("${lunchmenu.search}")
    private String url;

    @Autowired
    public LunchMenuServiceImpl(@Qualifier("zomato")HttpEntity<String> entity, RestTemplate restTemplate, Logger logger, MergerService mergerService) {
        this.entity = entity;
        this.restTemplate = restTemplate;
        this.logger = logger;
        this.mergerService = mergerService;
    }

    @Override
    public List<LunchMenuDemand> lunchMenuDownload(List<String> restaurantsIDs, List<LunchMenuDemand> demands) throws IOException, MessagingException {

        Map<String, LunchMenu> lunchMenuMap = new HashMap<>();

        for(String id: restaurantsIDs){

            try{
                ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class, id);
                lunchMenuMap.put(id, createLunchMenu(responseEntity));
                logger.info("Lunch menu downloaded for restaurant with ID [" + id + "]");
            }catch (HttpClientErrorException e){
                if(e.getResponseBodyAsString().contains("No Daily Menu Available")){
                    logger.warning("Restaurant with ID [" + id + "] has no lunch menu");
                    continue;
                }
                throw e;
            }

        }
        return mergerService.mergeLunchMenusWithRestaurants(lunchMenuMap, demands);
    }


    private LunchMenu createLunchMenu(ResponseEntity responseEntity) throws IOException {

        LunchMenu lunchMenu = new LunchMenu();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode dailyMenus = objectMapper.readTree(responseEntity.getBody().toString()).path("daily_menus");

        if(dailyMenus.isArray()){

            for(JsonNode menuNode: dailyMenus){

                JsonNode dailyMenu = menuNode.path("daily_menu");

                // only for today
                if(!dailyMenu.get("start_date").textValue().contains(simpleDateFormat.format(new Date()))){
                    continue;
                }

                lunchMenu
                        .setStartDate(dailyMenu.get("start_date").textValue())
                        .setName(dailyMenu.get("name").textValue());

                JsonNode dishes = dailyMenu.path("dishes");

                if(dishes.isArray()){

                    Dish dish = null;

                    for(JsonNode dishesNode : dishes){

                        JsonNode dishNode = dishesNode.path("dish");

                        if(dish == null){
                            dish = new Dish();
                        }

                        String dishName = dishNode.get("name").textValue();
                        String dishPrice = dishNode.get("price").textValue();

                        if(dishName.charAt(0) != '(' && dishName.charAt(dishName.length() - 1) != ')'){
                            if(dish.getName() != null){
                                lunchMenu.addDish(dish);
                            }
                            dish = new Dish();
                        }

                        dish
                                .setName(dishName)
                                .setPrice(dishPrice);

                        if(dishPrice.isEmpty()){
                            continue;
                        }

                        lunchMenu.addDish(dish);
                        dish = null;
                    }

                }
            }
        }
        return lunchMenu;
    }

}
