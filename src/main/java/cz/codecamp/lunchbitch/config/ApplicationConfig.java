package cz.codecamp.lunchbitch.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import services.restaurant_search_service.config.RestaurantConfiguration;

@Configuration
@ComponentScan(basePackageClasses = {RestaurantConfiguration.class})
public class ApplicationConfig {
}
