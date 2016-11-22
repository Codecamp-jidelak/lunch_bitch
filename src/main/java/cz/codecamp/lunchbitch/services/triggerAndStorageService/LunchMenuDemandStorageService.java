package cz.codecamp.lunchbitch.services.triggerAndStorageService;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;

import java.util.List;

public interface LunchMenuDemandStorageService {

	void saveLunchDemand(LunchMenuDemand demand);

	List<LunchMenuDemand> saveLunchDemandAndTriggerAllSending(LunchMenuDemand demand);

	LunchMenuDemand getLunchMenuDemand(String email);

	void deleteLunchMenuDemand(String email);

}
