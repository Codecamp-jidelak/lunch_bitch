package cz.codecamp.lunchbitch.services.lunchMenuDemandService;


import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.services.triggerAndStorageService.LunchMenuDemandStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LunchMenuDemandServiceImpl implements LunchMenuDemandService{

    private final LunchMenuDemandStorageService lunchMenuDemandStorageService;

    private LunchMenuDemand lunchMenuDemand;

    @Autowired
    public LunchMenuDemandServiceImpl(LunchMenuDemandStorageService lunchMenuDemandStorageService) {
        this.lunchMenuDemandStorageService = lunchMenuDemandStorageService;
    }


    @Override
    public void saveLunchMenuPreferences(LunchMenuDemand lunchMenuDemand) {
        this.lunchMenuDemand = lunchMenuDemand;
        new Thread(new SaveLunchDemandServiceRunnable()).start();

    }

    @Override
    public void unsubscribeMenuPreferences(LunchMenuDemand lunchMenuDemand) {
        this.lunchMenuDemand = lunchMenuDemand;
        new Thread(new UnsubscribeMenuPreferencesRunnable()).start();
    }

    @Override
    public void getLunchMenuPreferences(String email) {
        lunchMenuDemandStorageService.getLunchMenuDemand(email);
    }


    private class SaveLunchDemandServiceRunnable implements Runnable {

        @Override
        public void run() {
            lunchMenuDemandStorageService.saveLunchDemandAndTriggerAllSending(lunchMenuDemand);
        }
    }

    private class UnsubscribeMenuPreferencesRunnable implements Runnable {

        @Override
        public void run() {
            lunchMenuDemandStorageService.deleteLunchMenuDemand(lunchMenuDemand);
        }
    }

}