package cz.codecamp.lunchbitch.services.triggerAndStorageService.storage.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "RestaurantInfo")
public class RestaurantInfoEntity {

	@Id
	private String zomatoId;

	@Column
	private String name;

	@Column
	private String address;

	@Column
	private String locality;

	@Column
	private String city;

	@Column
	private String latitude;

	@Column
	private String longitude;

	@Column
	private String zipcode;

	@Column
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
