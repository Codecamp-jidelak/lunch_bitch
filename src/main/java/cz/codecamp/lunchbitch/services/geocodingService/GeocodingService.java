package cz.codecamp.lunchbitch.services.geocodingService;


import cz.codecamp.lunchbitch.models.Location;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface GeocodingService {

    Location getCoordinates(String address) throws IOException;

}
