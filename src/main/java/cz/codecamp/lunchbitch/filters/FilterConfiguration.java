package cz.codecamp.lunchbitch.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean someFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(weekDayOnlyFilter());
        registration.addUrlPatterns("/trigger/*");
        registration.setName("weekDayOnlyFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean(name = "weekDayOnlyFilter")
    public Filter weekDayOnlyFilter() {
        return new WeekDayOnlyFilter();
    }


}
