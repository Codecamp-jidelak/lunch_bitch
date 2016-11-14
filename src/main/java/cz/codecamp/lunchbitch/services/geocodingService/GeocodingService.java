package cz.codecamp.lunchbitch.services.geocodingService;


import cz.codecamp.lunchbitch.models.Location;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Service
public interface GeocodingService {

    @ResponseBody
    Location getCoordinates(String address) throws IOException;

}
