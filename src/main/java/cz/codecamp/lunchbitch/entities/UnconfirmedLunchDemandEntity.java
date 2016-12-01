package cz.codecamp.lunchbitch.entities;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UnconfirmedLunchDemandEntity {

    @Id
    @Column
    private String email;

    @Column
    private String serializedLunchDemand;

    public UnconfirmedLunchDemandEntity() {
    }

    public UnconfirmedLunchDemandEntity(String email, String serializedLunchDemand) {
        this.email = email;
        this.serializedLunchDemand = serializedLunchDemand;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSerializedLunchDemand() {
        return serializedLunchDemand;
    }

    public void setSerializedLunchDemand(String serializedLunchDemand) {
        this.serializedLunchDemand = serializedLunchDemand;
    }
}
