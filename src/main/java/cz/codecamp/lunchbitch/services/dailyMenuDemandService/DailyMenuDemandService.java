package cz.codecamp.lunchbitch.services.dailyMenuDemandService;


import cz.codecamp.lunchbitch.models.DataModel;
import org.springframework.http.HttpStatus;


public interface DailyMenuDemandService {

    HttpStatus saveDailyMenuPreferences(DataModel dataModel);

}
