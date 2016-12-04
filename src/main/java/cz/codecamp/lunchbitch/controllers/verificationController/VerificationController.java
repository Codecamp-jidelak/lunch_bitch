package cz.codecamp.lunchbitch.controllers.verificationController;

import cz.codecamp.lunchbitch.models.*;
import cz.codecamp.lunchbitch.models.exceptions.AccountDoesNotExistException;
import cz.codecamp.lunchbitch.models.exceptions.InvalidTokenException;
import cz.codecamp.lunchbitch.services.userActionService.AccountNotActivatedException;
import cz.codecamp.lunchbitch.services.userActionService.UserActionService;
import cz.codecamp.lunchbitch.services.webService.WebService;
import cz.codecamp.lunchbitch.webPageMappers.EmailForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class VerificationController {

    private final String EMAIL_FORM_ATTRIBUTE = "emailForm";
    private int COOKIE_MAX_AGE = 3600; // max 1 hour

    private final Logger logger;
    private final UserActionService userActionService;
    private final WebService webService;

    @Autowired
    public VerificationController(Logger logger, UserActionService userActionService, WebService webService) {
        this.logger = logger;
        this.userActionService = userActionService;
        this.webService = webService;
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.GET)
    public ModelAndView showUnsubscribeNoToken() {
        return new ModelAndView("unsubscribe");
    }

    @RequestMapping(value = "/unsubscribe/{authKey}", method = RequestMethod.GET)
    public ModelAndView showUnsubscribe(@PathVariable String authKey, Model model) {
        AuthToken authToken = new AuthToken(authKey, UserAction.UNSUBSCRIPTION);

        Email email = null;
        try {
            email = userActionService.authorizeDeleteAccount(authToken);
        } catch (InvalidTokenException e) {
            logger.info("Unsubscribe - invalid authKey: " + authToken.getAuthKey());
        }

        if (email != null) {
            model.addAttribute("recognized", true);
            model.addAttribute(EMAIL_FORM_ATTRIBUTE, new EmailForm(email.getEmailAdress()));
        }

        return new ModelAndView("unsubscribe");
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
    public String unsubscribeEmail(@Valid EmailForm emailForm, BindingResult bindingResult, RedirectAttributes attr,
                                   @CookieValue(name = "userAuthKey", required = false) String authKey, HttpServletResponse response) {

        attr.addFlashAttribute(EMAIL_FORM_ATTRIBUTE, emailForm);

        if (bindingResult.hasErrors()) {
            attr.addFlashAttribute(getAttributeName(EMAIL_FORM_ATTRIBUTE), bindingResult);
            logger.warning(bindingResult.getAllErrors().toString());
            return "redirect:/unsubscribe";
        }

        attr.addFlashAttribute("recognized", true);

        Email email = new Email(emailForm.getEmail());
        userActionService.deleteUserAccount(email);
        webService.clearCurrentProgress();

        if (authKey != null) {
            response.addCookie(deleteCookie(authKey));
        }

        logger.info("Unsubscribed e-mail: " + email.getEmailAdress());

        return "redirect:/unsubscribe?success=true";
    }


    private String getAttributeName(String attributeName) {
        return "org.springframework.validation.BindingResult." + attributeName;
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public ModelAndView showActivationPageNoToken() {
        return new ModelAndView("activate");
    }

    @RequestMapping(value = "/activate/{authKey}", method = RequestMethod.GET)
    public ModelAndView showActivationPage(@PathVariable String authKey, Model model) {
        AuthToken authToken = new AuthToken(authKey, UserAction.REGISTRATION);

        Email email = null;
        try {
            email = userActionService.activateRegistration(authToken);
        } catch (InvalidTokenException e) {
            logger.info("Activate - invalid authKey: " + authToken.getAuthKey());
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, "Activate - illegalStateException", e);
        }

        if (email != null) {
            model.addAttribute("activated", true);
            model.addAttribute("emailAddress", email.getEmailAdress());
            logger.info("Activated registration for e-mail: " + email.getEmailAdress());
        }

        return new ModelAndView("activate");
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView showSettingsNoToken(@CookieValue(name = "userAuthKey", required = false) String authKey, Model model) {

        if (authKey != null) {
            
            AuthToken authToken = new AuthToken(authKey, UserAction.REGISTRATION);
            
            LunchMenuDemand lunchMenuDemand = null;
            try {
                lunchMenuDemand = userActionService.authorizeUpdateDemand(authToken);
            } catch (InvalidTokenException e) {
                logger.info("Settings - invalid authKey: " + authToken.getAuthKey());
            }
            
            if (lunchMenuDemand != null) {
                model.addAttribute("recognized", true);
                model.addAttribute(EMAIL_FORM_ATTRIBUTE, new EmailForm(lunchMenuDemand.getEmail()));
                webService.loadUsersSettings(lunchMenuDemand);
            }
        }

        if (!model.containsAttribute(EMAIL_FORM_ATTRIBUTE)) {
            model.addAttribute(EMAIL_FORM_ATTRIBUTE, new EmailForm());
        }

        return new ModelAndView("settings");
    }


    @RequestMapping(value = "/settings/{authKey}", method = RequestMethod.GET)
    public ModelAndView showSettings(@PathVariable String authKey, Model model, HttpServletResponse response) {
        AuthToken authToken = new AuthToken(authKey, UserAction.UPDATE);

        LunchMenuDemand lunchMenuDemand = null;
        try {
            lunchMenuDemand = userActionService.authorizeUpdateDemand(authToken);
        } catch (InvalidTokenException e) {
            logger.info("Settings - invalid authKey: " + authToken.getAuthKey());
        }

        if (lunchMenuDemand != null) {

            response.addCookie(createCookie(authToken.getAuthKey()));
            model.addAttribute("recognized", true);
            model.addAttribute(EMAIL_FORM_ATTRIBUTE, new EmailForm(lunchMenuDemand.getEmail()));
            webService.loadUsersSettings(lunchMenuDemand);
            logger.info("Settings - access for user with e-mail: " + lunchMenuDemand.getEmail());
        }

        if (!model.containsAttribute(EMAIL_FORM_ATTRIBUTE)) {
            model.addAttribute(EMAIL_FORM_ATTRIBUTE, new EmailForm());
        }

        return new ModelAndView("settings");
    }


    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public String changeSettings(@Valid EmailForm emailForm, BindingResult bindingResult, RedirectAttributes attr) {

        attr.addFlashAttribute(EMAIL_FORM_ATTRIBUTE, emailForm);

        if (bindingResult.hasErrors()) {
            attr.addFlashAttribute(getAttributeName(EMAIL_FORM_ATTRIBUTE), bindingResult);
            logger.warning(bindingResult.getAllErrors().toString());
            return "redirect:/settings";
        }

        RegistrationState registrationState;
        try {
            userActionService.submitUpdateOrDeleteRequest(new Email(emailForm.getEmail()));
            registrationState = RegistrationState.SUCCESS;
            logger.info("Settings: Registered user with email " + emailForm.getEmail());
        } catch (AccountNotActivatedException e) {
            registrationState = RegistrationState.INACTIVE;
            logger.log(Level.WARNING, "Settings - accountNotActivatedException", e);
        } catch (AccountDoesNotExistException e) {
            registrationState = RegistrationState.NOTEXISTS;
            logger.log(Level.WARNING, "Settings - accountDoesNotExistException", e);
        }

        attr.addFlashAttribute("registrationState", registrationState.getState());
        return "redirect:/settings";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logOutView() {
        return new ModelAndView("logout");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ModelAndView logOutUser(@CookieValue(name = "userAuthKey", required = false) String authKey, HttpServletResponse response) {

        webService.clearCurrentProgress();

        if (authKey != null) {
            response.addCookie(deleteCookie(authKey));
        }

        return new ModelAndView("logout");
    }

    private Cookie createCookie(String authKey) {
        Cookie cookie = new Cookie("userAuthKey", authKey);
        cookie.setMaxAge(COOKIE_MAX_AGE);
        cookie.setPath("/");
        return cookie;
    }

    private Cookie deleteCookie(String authKey) {
        Cookie cookie = new Cookie("userAuthKey", authKey);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }
}
