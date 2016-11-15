package cz.codecamp.lunchbitch.models;

@SuppressWarnings("unused")
public class Location {

    private String address;

    private String locality;

    private String city;

    private String latitude;

    private String longitude;

    private String zipcode;

    private String countryId;

    public String getAddress() {
        return address;
    }

    public Location setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getLocality() {
        return locality;
    }

    public Location setLocality(String locality) {
        this.locality = locality;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Location setCity(String city) {
        this.city = city;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public Location setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public Location setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Location setZipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public String getCountryId() {
        return countryId;
    }

    public Location setCountryId(String countryId) {
        this.countryId = countryId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return address != null ? address.equals(location.address) : location.address == null
                && (locality != null ? locality.equals(location.locality) : location.locality == null
                && (city != null ? city.equals(location.city) : location.city == null
                && (latitude != null ? latitude.equals(location.latitude) : location.latitude == null
                && (longitude != null ? longitude.equals(location.longitude) : location.longitude == null
                && (zipcode != null ? zipcode.equals(location.zipcode) : location.zipcode == null
                && (countryId != null ? countryId.equals(location.countryId) : location.countryId == null))))));

    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (locality != null ? locality.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        result = 31 * result + (countryId != null ? countryId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return address;
    }
}
