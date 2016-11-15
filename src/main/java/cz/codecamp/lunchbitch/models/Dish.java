package cz.codecamp.lunchbitch.models;

@SuppressWarnings("unused")
public class Dish {

    private String name;

    private String price;

    public String getName() {
        return name;
    }

    public Dish setName(String name) {
        if(this.name == null){
            this.name = name;
        }else{
            this.name += "\n" + name;
        }
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
                        (price == null || price.isEmpty() ? "" :
                                "<p style=\"font-family:courier;\">" +
                                "cena: " + price +
                                "</p>") +
                "   </td>" +
                "</tr>";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;
        return price != null ? price.equals(dish.price) : dish.price == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
