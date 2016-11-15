package cz.codecamp.lunchbitch.models;

@SuppressWarnings("unused")
public class Restaurant {

    private String id;

    private String name;

    private Location location;

    private LunchMenu lunchmenu;

    public String getId() {
        return id;
    }

    public LunchMenu getLunchmenu() {
        return lunchmenu;
    }

    public Restaurant setLunchmenu(LunchMenu lunchmenus) {
        this.lunchmenu = lunchmenus;
        return this;
    }

    public Restaurant setId(String id) {
        this.id = id;
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

    public boolean hasLocation(){
        return location != null;
    }

    public boolean hasLunchMenu(){
        return lunchmenu != null && !lunchmenu.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant that = (Restaurant) o;

        return id != null ? id.equals(that.id) : that.id == null
                && (name != null ? name.equals(that.name) : that.name == null
                && (location != null ? location.equals(that.location) : that.location == null
                && (lunchmenu != null ? lunchmenu.equals(that.lunchmenu) : that.lunchmenu == null)));

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (lunchmenu != null ? lunchmenu.hashCode() : 0);
        return result;
    }
}
