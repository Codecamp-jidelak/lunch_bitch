package cz.codecamp.lunchbitch.entities;

import cz.codecamp.lunchbitch.models.UserAction;
import cz.codecamp.lunchbitch.models.UserActionRequestState;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Entity
public class UserActionRequestEntity {
    @Id
    @Column
    private Long id;

    @Column
    private String email;

    @Column
    private String key;

    @Column
    @Enumerated(EnumType.STRING)
    private UserAction action;

    @Column
    @Enumerated(EnumType.STRING)
    private UserActionRequestState state;

    @Column
    private LocalDateTime timeCreated;

    public static UserActionRequestEntity activateNew(String email, String authKey, UserAction userAction) {
        UserActionRequestEntity userActionRequestEntity = new UserActionRequestEntity();
        userActionRequestEntity.setEmail(email);
        userActionRequestEntity.setKey(authKey);
        userActionRequestEntity.setAction(userAction);
        userActionRequestEntity.setState(UserActionRequestState.ACTIVE);
        userActionRequestEntity.setTimeCreated(now());
        return userActionRequestEntity;
    }

    public UserActionRequestEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public UserAction getAction() {
        return action;
    }

    public void setAction(UserAction action) {
        this.action = action;
    }

    public UserActionRequestState getState() {
        return state;
    }

    public void setState(UserActionRequestState state) {
        this.state = state;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public UserActionRequestEntity complete() {
        return this;
    }

    public UserActionRequestEntity expire() {
        return this;
    }

    public boolean isActive() {
        return state == UserActionRequestState.ACTIVE;
    }
}
