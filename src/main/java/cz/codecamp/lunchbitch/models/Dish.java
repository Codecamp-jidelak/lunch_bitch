package cz.codecamp.lunchbitch.models;

@SuppressWarnings("unused")
public class Dish {

    private String dish_id;

    private String name;

    private String price;

    public String getDish_id() {
        return dish_id;
    }

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dishes = (Dish) o;

        return dish_id != null ? dish_id.equals(dishes.dish_id) : dishes.dish_id == null
                && (name != null ? name.equals(dishes.name) : dishes.name == null
                && (price != null ? price.equals(dishes.price) : dishes.price == null));

    }

    @Override
    public int hashCode() {
        int result = dish_id != null ? dish_id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dish_id='" + dish_id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
