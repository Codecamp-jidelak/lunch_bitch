package cz.codecamp.lunchbitch.models;


import java.util.ArrayList;
import java.util.List;

public class LunchMenu {

    private String startDate;

    private String endDate;

    private String name;

    private List<Dish> dishes;

    public LunchMenu (){
        dishes = new ArrayList<>();
    }

    public String getStartDate() {
        return startDate;
    }

    public LunchMenu setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public LunchMenu setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getName() {
        return name;
    }

    public LunchMenu setName(String name) {
        this.name = name;
        return this;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public LunchMenu setDishes(List<Dish> dishes) {
        this.dishes = dishes;
        return this;
    }

    public void addDish(Dish dish){
        dishes.add(dish);
    }

    @Override
    public String toString() {
        return "LunchMenu{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", name='" + name + '\'' +
                ", dishes=" + dishes +
                '}';
    }
}
