package cz.codecamp.lunchbitch.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import static java.time.LocalDateTime.now;

@Component
public class WeekDayOnlyFilter implements Filter {

    @Autowired
    private Logger LOGGER;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (isWeekend(now())) {
            LOGGER.info("Skipping sending of lunch menus due to weekend.");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    protected boolean isWeekend(LocalDateTime dateTime) {
        DayOfWeek currentDayOfWeek = dateTime.getDayOfWeek();
        return currentDayOfWeek == DayOfWeek.SATURDAY || currentDayOfWeek == DayOfWeek.SUNDAY;
    }


}


