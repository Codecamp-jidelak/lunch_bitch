package cz.codecamp.lunchbitch.models;


import java.util.List;

public class LunchMenu {

    private String daily_menu_id;

    private String name;

    private String start_date;

    private String end_date;

    private List<Dishes> dishes;

    public List<Dishes> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dishes> dishes) {
        this.dishes = dishes;
    }

    public String getDaily_menu_id() {
        return daily_menu_id;
    }

    public void setDaily_menu_id(String daily_menu_id) {
        this.daily_menu_id = daily_menu_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LunchMenu lunchMenu = (LunchMenu) o;

        return daily_menu_id != null ? daily_menu_id.equals(lunchMenu.daily_menu_id) : lunchMenu.daily_menu_id == null
                && (name != null ? name.equals(lunchMenu.name) : lunchMenu.name == null
                && (start_date != null ? start_date.equals(lunchMenu.start_date) : lunchMenu.start_date == null
                && (end_date != null ? end_date.equals(lunchMenu.end_date) : lunchMenu.end_date == null
                && (dishes != null ? dishes.equals(lunchMenu.dishes) : lunchMenu.dishes == null))));

    }

    @Override
    public int hashCode() {
        int result = daily_menu_id != null ? daily_menu_id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (start_date != null ? start_date.hashCode() : 0);
        result = 31 * result + (end_date != null ? end_date.hashCode() : 0);
        result = 31 * result + (dishes != null ? dishes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LunchMenu{" +
                "daily_menu_id='" + daily_menu_id + '\'' +
                ", name='" + name + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", dishes=" + (dishes != null ? dishes.toString() : null) +
                '}';
    }
}
