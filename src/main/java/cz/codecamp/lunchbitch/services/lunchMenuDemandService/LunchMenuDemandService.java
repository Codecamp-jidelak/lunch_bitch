package cz.codecamp.lunchbitch.services.lunchMenuDemandService;


import cz.codecamp.lunchbitch.models.LunchMenuDemand;


public interface LunchMenuDemandService {

    /**
     * Save/Update lunch menu preferences of subscriber
     * @param lunchMenuDemand object which will be stored in DB
     */
    void saveLunchMenuPreferences(LunchMenuDemand lunchMenuDemand);

    /**
     * Delete lunch menu preferences from DB
     * @param email for unsubscribe lunch menu preferences
     */
    void unsubscribeMenuPreferences(String email);

    /**
     * Get lunch menu settings from DB by email
     * @param email of subscriber
     */
    void getLunchMenuPreferences(String email);

}
