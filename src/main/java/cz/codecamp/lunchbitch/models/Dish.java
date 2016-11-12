package cz.codecamp.lunchbitch.models;

@SuppressWarnings("unused")
public class Dish {

    private String name;

    private String price;

    public String getName() {
        return name;
    }

    public Dish setName(String name) {
        this.name = name;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public Dish setPrice(String price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
