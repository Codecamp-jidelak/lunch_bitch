package cz.codecamp.lunchbitch.controllers.webController;

import cz.codecamp.lunchbitch.models.Restaurant;
import cz.codecamp.lunchbitch.services.lunchMenuDemandService.LunchMenuDemandService;
import cz.codecamp.lunchbitch.services.restaurantSearchService.RestaurantSearchService;
import cz.codecamp.lunchbitch.services.webService.WebService;
import cz.codecamp.lunchbitch.webPageMappers.ResultsWebPage;
import cz.codecamp.lunchbitch.webPageMappers.SetupWebPage;
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

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView startSearching() {
        return new ModelAndView("search");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchKeyword(@Valid String keyword) {

        if(keyword != null && !keyword.trim().isEmpty()) {

            try {
                webService.saveSearchResult(restaurantSearchService.searchForRestaurants(keyword));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "redirect:/results";
        }
        return "redirect:/search";
    }

    @ModelAttribute(value = "foundRestaurants")
    public List<Restaurant> getFoundRestaurants() {
        return webService.getFoundRestaurants();
    }

    @RequestMapping(value = "/results", method = RequestMethod.GET)
    public ModelAndView getResults(Model model) {
        Map<String, Object> mapModel = new HashMap<>();
        mapModel.put("foundRestaurant", new Restaurant());
        model.addAttribute("resultsWebPage", new ResultsWebPage());
        return new ModelAndView("results", mapModel);
    }

    @RequestMapping(value = "/results", method = RequestMethod.POST)
    public String selectRestaurants(@ModelAttribute("resultsWebPage") ResultsWebPage resultsWebPage, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "redirect:/results";
        }
        webService.addSelectedRestaurantIDs(resultsWebPage.getRestaurantIDs());
        return "redirect:/setup";
    }

    @ModelAttribute(value = "selectedRestaurants")
    public List<Restaurant> getSelectedRestaurants() {
        return webService.getSelectedRestaurants();
    }

    @RequestMapping(value = "/setup", method = RequestMethod.GET)
    public ModelAndView showSelectedRestaurants(Model model) {
        Map<String, Object> mapModel = new HashMap<>();
        mapModel.put("selectedRestaurant", new Restaurant());
        model.addAttribute("setupWebPage", new SetupWebPage(webService.getSelectedRestaurants()));
        return new ModelAndView("setup", mapModel);
    }

    @RequestMapping(value = "/setup", method = RequestMethod.POST)
    public String selectRestaurants(@ModelAttribute("setupWebPage") SetupWebPage setupWebPage, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "redirect:/setup";
        }
        webService.updateSelectedRestaurantIDs(setupWebPage.getRestaurantIDs());
        return "redirect:/setup";
    }


    @RequestMapping(value = "/success", method = RequestMethod.POST)
    public String sendAway(@Valid String email) {

        // to do proper email validation
        if(email != null && !email.trim().isEmpty()) {

            webService.setLunchMenuDemandEmail(email);
            lunchMenuDemandService.saveLunchMenuPreferences(webService.getLunchMenuDemandPreferences());
            return "redirect:/success";
        }
        return "redirect:/setup";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ModelAndView confirm() {
        Map<String, String> model = new HashMap<>();
        model.put("emailAddress", webService.getEmail());
        return new ModelAndView("success", model);
    }
}
