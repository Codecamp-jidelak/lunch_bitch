package cz.codecamp.lunchbitch.services.triggerAndStorageService.storage.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "RestaurantSelection")
public class UsersRestaurantSelectionEntity {

	@Id
	@GeneratedValue
	private long id;

	@Column
	private String email;

	@Column
	private String zomatoRestaurantId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZomatoRestaurantId() {
		return zomatoRestaurantId;
	}

	public void setZomatoRestaurantId(String zomatoRestaurantId) {
		this.zomatoRestaurantId = zomatoRestaurantId;
	}
}
