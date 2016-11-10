package cz.codecamp.lunchbitch.controllers.webController;

import cz.codecamp.lunchbitch.models.Restaurant;
import cz.codecamp.lunchbitch.models.RestaurantIDs;
import cz.codecamp.lunchbitch.services.lunchMenuDemandService.LunchMenuDemandService;
import cz.codecamp.lunchbitch.services.restaurantSearchService.RestaurantSearchService;
import cz.codecamp.lunchbitch.services.webService.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    private final WebService webService;
    private final RestaurantSearchService restaurantSearchService;
    private final LunchMenuDemandService lunchMenuDemandService;

    @Autowired
    public WebController(WebService webService, RestaurantSearchService restaurantSearchService, LunchMenuDemandService lunchMenuDemandService) {
        this.webService = webService;
        this.restaurantSearchService = restaurantSearchService;
        this.lunchMenuDemandService = lunchMenuDemandService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getIndex() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String searchKeyword(@Valid String keyword) {

        if(keyword != null && !keyword.trim().isEmpty()) {

            try {
                webService.saveSearchResult(restaurantSearchService.searchForRestaurants(keyword));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "redirect:/results";
        }
        return "redirect:/";
    }

    @ModelAttribute(value = "foundRestaurants")
    public List<Restaurant> getFoundRestaurants() {
        return webService.getFoundRestaurants();
    }

    @RequestMapping(value = "/results", method = RequestMethod.GET)
    public ModelAndView getResults(Model model) {
        Map<String, Object> mapModel = new HashMap<>();
        mapModel.put("foundRestaurant", new Restaurant());
        model.addAttribute("restaurantIDs", new RestaurantIDs());
        return new ModelAndView("results", mapModel);
    }

    @RequestMapping(value = "/results", method = RequestMethod.POST)
    public String selectRestaurants(@ModelAttribute("restaurantIDs") RestaurantIDs restaurantIDs, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "redirect:/results";
        }
        webService.addSelectedRestaurantIDs(restaurantIDs.getRestaurantIDs());
        return "redirect:/save";
    }

    @ModelAttribute(value = "selectedRestaurants")
    public List<Restaurant> getSelectedRestaurants() {
        return webService.getSelectedRestaurants();
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public ModelAndView showSelectedRestaurants() {
        Map<String, Object> model = new HashMap<>();
        model.put("selectedRestaurant", new Restaurant());
        return new ModelAndView("save", model);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String sendAway(@Valid String email) {
        // to do proper email validation
        if(email != null && !email.trim().isEmpty()) {

            webService.setLunchMenuDemandEmail(email);
            lunchMenuDemandService.saveLunchMenuPreferences(webService.getLunchMenuDemandPreferences());
            return "redirect:/success";
        }
        return "redirect:/save";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ModelAndView confirm() {
        Map<String, String> model = new HashMap<>();
        model.put("emailAddress", webService.getEmail());
        return new ModelAndView("success", model);
    }
}
