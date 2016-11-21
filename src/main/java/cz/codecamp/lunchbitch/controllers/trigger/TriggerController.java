package cz.codecamp.lunchbitch.controllers.trigger;

import cz.codecamp.lunchbitch.services.triggerAndStorageService.LunchMenuSendingTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/trigger")
public class TriggerController {

    @Autowired
    private LunchMenuSendingTrigger trigger;

    @RequestMapping(method = RequestMethod.POST)
    public void trigger(@RequestBody String password) {
        trigger.triggerSending(password);
    }
}
