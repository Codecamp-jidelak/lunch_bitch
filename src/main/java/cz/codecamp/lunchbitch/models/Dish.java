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
        return
                "<tr>" +
                "   <td align=\"center\">" +
                "       <p style=\"font-family:courier;\">" +
                        name +
                "       </p>" +
                        (price.isEmpty() ? "" :
                                "<p style=\"font-family:courier;\">" +
                                "cena: " + price +
                                "</p>") +
                "   </td>" +
                "</tr>";
    }
}
