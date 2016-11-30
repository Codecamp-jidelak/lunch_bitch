package cz.codecamp.lunchbitch.controllers.webController;

import cz.codecamp.lunchbitch.models.Restaurant;
import cz.codecamp.lunchbitch.services.webService.WebService;
import cz.codecamp.lunchbitch.webPageMappers.EmailForm;
import cz.codecamp.lunchbitch.webPageMappers.ResultsWebPage;
import cz.codecamp.lunchbitch.webPageMappers.SearchForm;
import cz.codecamp.lunchbitch.webPageMappers.SetupWebPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@PropertySource(value = "/message.properties", encoding="UTF-8")
public class WebController {

    private final String SEARCH_FORM_ATTRIBUTE = "searchForm";
    private final String RESULTS_WEB_PAGE_ATTRIBUTE = "resultsWebPage";
    private final String SETUP_WEB_PAGE_ATTRIBUTE = "setupWebPage";
    private final String EMAIL_FORM_ATTRIBUTE = "emailForm";

    @Value("${error.no.restaurant}")
    private String ERROR_NO_RESTAURANT_MSG;

    private final Logger logger;

    private final WebService webService;
    private final SearchForm searchForm;

    @Autowired
    public WebController(Logger logger, WebService webService) {
        this.logger = logger;
        this.webService = webService;
        searchForm = new SearchForm();
        searchForm.setType("keyword");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getIndex(Model model) {
        if (!webService.isEmptySelectedRestaurantsList()) {
            model.addAttribute("selected", true);
        }
        return new ModelAndView("index");
    }


    @ModelAttribute(value = "rbOptions")
    public List<String> getRadioButtonOptions() {
        List<String> options = new ArrayList<>();
        options.add("keyword");
        options.add("address");
        return options;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView startSearching(Model model) {
        if (!model.containsAttribute(SEARCH_FORM_ATTRIBUTE)) {
            model.addAttribute(SEARCH_FORM_ATTRIBUTE, searchForm);
        }
        if (!webService.isEmptySelectedRestaurantsList()) {
            model.addAttribute("selected", true);
        }
        return new ModelAndView("search");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchKeyword(@Valid SearchForm searchForm, BindingResult bindingResult, RedirectAttributes attr) {
        String calledFrom = searchForm.getCalledFrom();
        if (calledFrom == null) {
            calledFrom = "search";
        }

        if (bindingResult.hasErrors()) {
            logger.warning(bindingResult.getAllErrors().toString());
            attr.addFlashAttribute(getAttributeName(SEARCH_FORM_ATTRIBUTE), bindingResult);
            attr.addFlashAttribute(SEARCH_FORM_ATTRIBUTE, searchForm);
            return "redirect:/" + calledFrom;
        }

        if (webService.saveSearchResult(searchForm)) {
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
        if (!webService.isEmptySelectedRestaurantsList()) {
            model.addAttribute("selected", true);
        }
        if (!model.containsAttribute(RESULTS_WEB_PAGE_ATTRIBUTE)) {
            model.addAttribute(RESULTS_WEB_PAGE_ATTRIBUTE, new ResultsWebPage());
        }
        if (!model.containsAttribute(SEARCH_FORM_ATTRIBUTE)) {
            model.addAttribute(SEARCH_FORM_ATTRIBUTE, searchForm);
        }
        return new ModelAndView("results", mapModel);
    }

    @RequestMapping(value = "/results", method = RequestMethod.POST)
    public String selectFromFoundRestaurants(@ModelAttribute(RESULTS_WEB_PAGE_ATTRIBUTE) ResultsWebPage resultsWebPage, BindingResult bindingResult, RedirectAttributes attr) {

        if (resultsWebPage.getRestaurantIDs().isEmpty()) {
            bindingResult.rejectValue("restaurantIDs", "messageCode", ERROR_NO_RESTAURANT_MSG);
        }

        if (bindingResult.hasErrors()) {
            logger.warning(bindingResult.getAllErrors().toString());
            attr.addFlashAttribute(getAttributeName(RESULTS_WEB_PAGE_ATTRIBUTE), bindingResult);
            attr.addFlashAttribute(RESULTS_WEB_PAGE_ATTRIBUTE, resultsWebPage);
            return "redirect:/results#results";
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
        model.addAttribute(SETUP_WEB_PAGE_ATTRIBUTE, new SetupWebPage(webService.getSelectedRestaurants()));
        if (!model.containsAttribute(EMAIL_FORM_ATTRIBUTE)) {
            model.addAttribute(EMAIL_FORM_ATTRIBUTE, new EmailForm());
        }
        if (!model.containsAttribute(SEARCH_FORM_ATTRIBUTE)) {
            model.addAttribute(SEARCH_FORM_ATTRIBUTE, searchForm);
        }
        return new ModelAndView("setup", mapModel);
    }

    @RequestMapping(value = "/setup", method = RequestMethod.POST)
    public String updateSelectedRestaurants(@ModelAttribute(SETUP_WEB_PAGE_ATTRIBUTE) SetupWebPage setupWebPage, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warning(bindingResult.getAllErrors().toString());
            return "setup";
        }
        webService.updateSelectedRestaurantIDs(setupWebPage.getRestaurantIDs());
        return "redirect:/setup";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ModelAndView confirm() {
        Map<String, String> model = new HashMap<>();
        model.put("emailAddress", webService.getEmail());
        return new ModelAndView("success", model);
    }

    @RequestMapping(value = "/success", method = RequestMethod.POST)
    public String sendAway(@Valid EmailForm emailForm, BindingResult bindingResult, RedirectAttributes attr) {

        if (webService.isEmptySelectedRestaurantsList()) {
            bindingResult.rejectValue("email", "messageCode", ERROR_NO_RESTAURANT_MSG);
        }

        if (bindingResult.hasErrors()) {
            logger.warning(bindingResult.getAllErrors().toString());
            attr.addFlashAttribute(getAttributeName(EMAIL_FORM_ATTRIBUTE), bindingResult);
            attr.addFlashAttribute(EMAIL_FORM_ATTRIBUTE, emailForm);
            return "redirect:/setup#send";
        }

        webService.setLunchMenuDemandEmail(emailForm.getEmail());
        webService.saveLunchMenuPreferences();
        logger.info("Created new LunchMenuPreferences for e-mail: " + emailForm.getEmail());
        return "redirect:/success";
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.GET)
    public ModelAndView showUnsubscribe(Model model) {
        if (!model.containsAttribute(EMAIL_FORM_ATTRIBUTE)) {
            model.addAttribute(EMAIL_FORM_ATTRIBUTE, new EmailForm());
        }
        return new ModelAndView("unsubscribe");
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
    public String unsubscribeEmail(@Valid EmailForm emailForm, BindingResult bindingResult, RedirectAttributes attr) {

        if (bindingResult.hasErrors()) {
            logger.warning(bindingResult.getAllErrors().toString());
            return "unsubscribe";
        }
        webService.unsubscribeMenuPreferences(emailForm.getEmail());
        attr.addFlashAttribute(getAttributeName(EMAIL_FORM_ATTRIBUTE), bindingResult);
        attr.addFlashAttribute(EMAIL_FORM_ATTRIBUTE, emailForm);
        logger.info("Unsubscribed e-mail: " + emailForm.getEmail());
        return "redirect:/unsubscribe?success=true";
    }

    private String getAttributeName(String attributeName) {
        return "org.springframework.validation.BindingResult." + attributeName;
    }

}
