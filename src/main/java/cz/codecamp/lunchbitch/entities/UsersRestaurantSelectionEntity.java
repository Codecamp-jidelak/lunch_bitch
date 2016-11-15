package cz.codecamp.lunchbitch.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "RestaurantSelection")
@Table(name = "restaurant_selection")
public class UsersRestaurantSelectionEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "email", nullable = false)
	private String email;


	@Column(name = "zomato_restaurant_id", nullable = false)
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
