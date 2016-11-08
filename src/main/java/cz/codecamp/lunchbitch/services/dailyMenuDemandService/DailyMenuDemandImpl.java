package cz.codecamp.lunchbitch.services.dailyMenuDemandService;


import cz.codecamp.lunchbitch.models.DataModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DailyMenuDemandImpl implements DailyMenuDemandService {

    public HttpStatus saveDailyMenuPreferences(DataModel dataModel){
        System.out.println(dataModel.toString());
        //TODO: request to CallToAction service
        return HttpStatus.CREATED;
    }

}
