package cz.codecamp.lunchbitch.services.triggerAndStorageService;

import cz.codecamp.lunchbitch.converters.RestaurantConverters;
import cz.codecamp.lunchbitch.entities.RestaurantInfoEntity;
import cz.codecamp.lunchbitch.entities.UserActionRequestEntity;
import cz.codecamp.lunchbitch.entities.UsersRestaurantSelectionEntity;
import cz.codecamp.lunchbitch.models.*;
import cz.codecamp.lunchbitch.repositories.RestaurantInfoRepository;
import cz.codecamp.lunchbitch.repositories.UserActionRequestRepository;
import cz.codecamp.lunchbitch.repositories.UsersRestaurantSelectionRepository;
import cz.codecamp.lunchbitch.services.lunchMenuService.LunchMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class LunchMenuSendingTriggerServiceImpl implements LunchMenuSendingTrigger {

    @Autowired
    private Logger LOGGER;

    @Autowired
    private UsersRestaurantSelectionRepository restaurantSelectionRepository;

    @Autowired
    private RestaurantInfoRepository restaurantInfoRepository;

    @Autowired
    private UserActionRequestRepository userActionRequestRepository;

    @Autowired
    private LunchMenuService lunchMenuService;

    @Value("${trigger.password}")
    String triggerPassword;

    @Transactional
    @Override
    public void triggerSending(String password) {
        validatePassword(password);
        onTrigger();
    }

    private void validatePassword(String password) {
        if (!password.equals(triggerPassword)) {
            throw new IllegalStateException("Wrong password");
        }
    }

    private List<LunchMenuDemand> onTrigger() {
        List<RestaurantInfoEntity> restaurantInfoEntities = retrieveAllRestaurantInfos();
        List<UsersRestaurantSelectionEntity> restaurantSelectionEntities = retrieveAllRestaurantSelections();
        List<Restaurant> restaurantDtos = convertToRestaurantDtos(restaurantInfoEntities);
        List<LunchMenuDemand> lunchMenuDemands = convertToLunchMenuDemands(restaurantSelectionEntities, restaurantDtos);
        List<String> restaurantIds = extractRestaurantIds(restaurantDtos);

        Map<String, AuthToken> unsubscribeTokens = retrieveUnsubscribeTokens();


        try {
            return lunchMenuService.lunchMenuDownload(restaurantIds, lunchMenuDemands, unsubscribeTokens);
        } catch (IOException | MessagingException e) {
            LOGGER.warning(e.getMessage());
            return lunchMenuDemands;
        }
    }

    private Map<String, AuthToken> retrieveUnsubscribeTokens() {
        List<UserActionRequestEntity> allUnsubscribeTokens = userActionRequestRepository.findByAction(UserAction.UNSUBSCRIPTION);
        return allUnsubscribeTokens.stream().collect(Collectors.toMap(UserActionRequestEntity::getEmail, this::convertToAuthToken));
    }

    private AuthToken convertToAuthToken(UserActionRequestEntity entity) {
        return new AuthToken(entity.getKey(), entity.getAction());
    }


    private List<RestaurantInfoEntity> retrieveAllRestaurantInfos() {
        return iterableToList(restaurantInfoRepository.findAll());
    }

    private List<UsersRestaurantSelectionEntity> retrieveAllRestaurantSelections() {
        return iterableToList(restaurantSelectionRepository.findAll());
    }

    private List<String> extractRestaurantIds(List<Restaurant> restaurantDtos) {
        return restaurantDtos.stream().map(Restaurant::getId).collect(toList());
    }

    private List<Restaurant> convertToRestaurantDtos(List<RestaurantInfoEntity> restaurantInfoEntities) {
        return restaurantInfoEntities.stream().map(RestaurantConverters::convertToRestaurantDto).collect(toList());
    }

    private List<LunchMenuDemand> convertToLunchMenuDemands(List<UsersRestaurantSelectionEntity> restaurantSelections, List<Restaurant> restaurantDtos) {
        Map<String, List<UsersRestaurantSelectionEntity>> restaurantSelectionsByEmails = groupByOrdered(restaurantSelections, UsersRestaurantSelectionEntity::getEmail);
        Map<String, Restaurant> restaurantsByZomatoIds = restaurantDtos.stream().collect(toMap(Restaurant::getId, identity()));
        List<LunchMenuDemand> demands = new ArrayList<>();
        for (String email : restaurantSelectionsByEmails.keySet()) {
            List<UsersRestaurantSelectionEntity> usersSelections = restaurantSelectionsByEmails.get(email);
            List<Restaurant> usersRestaurants = usersSelections
                    .stream()
                    .map(UsersRestaurantSelectionEntity::getZomatoRestaurantId)
                    .map(restaurantsByZomatoIds::get)
                    .collect(toList());
            LunchMenuDemand lunchMenuDemand = new LunchMenuDemand();
            lunchMenuDemand.setEmail(email);
            lunchMenuDemand.setRestaurants(usersRestaurants);
            demands.add(lunchMenuDemand);
        }
        return demands;
    }

    private <T> List<T> iterableToList(Iterable<T> iterable) {
        ArrayList<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return Collections.unmodifiableList(list);
    }

    private static <K, V> Map<K, List<V>> groupByOrdered(List<V> list, Function<V, K> keyFunction) {
        return list.stream()
                .collect(Collectors.groupingBy(
                        keyFunction,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    protected void setLunchMenuService(LunchMenuService service) {
        lunchMenuService = service;
    }

}
