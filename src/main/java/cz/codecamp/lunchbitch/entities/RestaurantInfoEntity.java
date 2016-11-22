package cz.codecamp.lunchbitch.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "RestaurantInfo")
@Table(name = "restaurant_info")
public class RestaurantInfoEntity implements Serializable {

	@Id
	@Column(name = "zomato_id", nullable = false)
	private String zomatoId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "address", nullable = true)
	private String address;

	@Column(name = "locality", nullable = true)
	private String locality;

	@Column(name = "city", nullable = true)
	private String city;

	@Column(name = "latitude", nullable = true)
	private String latitude;

	@Column(name = "longitude", nullable = true)
	private String longitude;

	@Column(name = "zipcode", nullable = true)
	private String zipcode;

	@Column(name = "country_id", nullable = true)
	private String countryId;

	public String getZomatoId() {
		return zomatoId;
	}

	public void setZomatoId(String zomatoId) {
		this.zomatoId = zomatoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
}
