package cz.codecamp.lunchbitch.models;


import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class DataModel {

    private List<Restaurant> restaurants;

    private String email;

    public DataModel (){
        restaurants = new ArrayList<>();
    }

    public void addRestaurant(Restaurant restaurant){
        restaurants.add(restaurant);
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurantList) {
        this.restaurants = restaurantList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataModel dataModel = (DataModel) o;

        return restaurants != null ? restaurants.equals(dataModel.restaurants) : dataModel.restaurants == null;

    }

    @Override
    public int hashCode() {
        return restaurants != null ? restaurants.hashCode() : 0;
    }
}
