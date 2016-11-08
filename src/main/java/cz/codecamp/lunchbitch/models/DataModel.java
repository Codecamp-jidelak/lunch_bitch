package cz.codecamp.lunchbitch.models;


import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class DataModel {

    private List<Restaurant> restaurantList;

    public DataModel (){
        restaurantList = new ArrayList<>();
    }

    public void addRestaurant(Restaurant restaurant){
        restaurantList.add(restaurant);
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataModel dataModel = (DataModel) o;

        return restaurantList != null ? restaurantList.equals(dataModel.restaurantList) : dataModel.restaurantList == null;

    }

    @Override
    public int hashCode() {
        return restaurantList != null ? restaurantList.hashCode() : 0;
    }
}
