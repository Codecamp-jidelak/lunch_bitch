package cz.codecamp.lunchbitch.services.triggerAndStorageService;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;

import java.util.List;

public interface LunchMenuSendingTrigger {

	List<LunchMenuDemand> onTrigger();
}
