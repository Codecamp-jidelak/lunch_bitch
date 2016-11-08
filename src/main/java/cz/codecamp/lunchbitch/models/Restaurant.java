package cz.codecamp.lunchbitch.models;

@SuppressWarnings("unused")
public class Restaurant {

    private String res_id;

    private String name;

    private Location location;

    private LunchMenu lunchmenu;

    public String getRes_id() {
        return res_id;
    }

    public LunchMenu getLunchmenu() {
        return lunchmenu;
    }

    public Restaurant setLunchmenu(LunchMenu lunchmenu) {
        this.lunchmenu = lunchmenu;
        return this;
    }

    public Restaurant setRes_id(String res_id) {
        this.res_id = res_id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Restaurant setName(String name) {
        this.name = name;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public Restaurant setLocation(Location location) {
        this.location = location;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant that = (Restaurant) o;

        return res_id != null ? res_id.equals(that.res_id) : that.res_id == null
                && (name != null ? name.equals(that.name) : that.name == null
                && (location != null ? location.equals(that.location) : that.location == null
                && (lunchmenu != null ? lunchmenu.equals(that.lunchmenu) : that.lunchmenu == null)));

    }

    @Override
    public int hashCode() {
        int result = res_id != null ? res_id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (lunchmenu != null ? lunchmenu.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "res_id='" + res_id + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", lunchmenu=" + lunchmenu +
                '}';
    }
}
