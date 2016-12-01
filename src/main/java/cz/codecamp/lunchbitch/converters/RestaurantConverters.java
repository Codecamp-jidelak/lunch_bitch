package cz.codecamp.lunchbitch.converters;

import cz.codecamp.lunchbitch.entities.RestaurantInfoEntity;
import cz.codecamp.lunchbitch.models.Location;
import cz.codecamp.lunchbitch.models.Restaurant;

public class RestaurantConverters {

    public static RestaurantInfoEntity convertToRestaurantInfoEntity(Restaurant restaurant) {
        Location restaurantsLocation = restaurant.getLocation();

        RestaurantInfoEntity restaurantInfoEntity = new RestaurantInfoEntity();
        restaurantInfoEntity.setZomatoId(restaurant.getId());
        restaurantInfoEntity.setName(restaurant.getName());
        restaurantInfoEntity.setAddress(restaurantsLocation.getAddress());
        restaurantInfoEntity.setLocality(restaurantsLocation.getLocality());
        restaurantInfoEntity.setCity(restaurantsLocation.getCity());
        restaurantInfoEntity.setLatitude(restaurantsLocation.getLatitude());
        restaurantInfoEntity.setLongitude(restaurantsLocation.getLongitude());
        restaurantInfoEntity.setZipcode(restaurantsLocation.getZipcode());
        restaurantInfoEntity.setCountryId(restaurantsLocation.getCountryId());
        return restaurantInfoEntity;
    }

    public static Restaurant convertToRestaurantDto(RestaurantInfoEntity restaurantInfoEntity) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantInfoEntity.getZomatoId());
        restaurant.setName(restaurantInfoEntity.getName());

        Location location = new Location();
        location.setAddress(restaurantInfoEntity.getAddress());
        location.setLocality(restaurantInfoEntity.getLocality());
        location.setCity(restaurantInfoEntity.getCity());
        location.setLatitude(restaurantInfoEntity.getLatitude());
        location.setLongitude(restaurantInfoEntity.getLongitude());
        location.setZipcode(restaurantInfoEntity.getZipcode());
        location.setCountryId(restaurantInfoEntity.getCountryId());

        restaurant.setLocation(location);
        return restaurant;
    }
}
