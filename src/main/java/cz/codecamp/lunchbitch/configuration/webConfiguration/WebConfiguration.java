package cz.codecamp.lunchbitch.configuration.webConfiguration;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.services.webService.WebService;
import cz.codecamp.lunchbitch.services.webService.WebServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

	@Bean
	public WebService webService() {
		return new WebServiceImpl(new LunchMenuDemand(), new LunchMenuDemand());
	}
}
