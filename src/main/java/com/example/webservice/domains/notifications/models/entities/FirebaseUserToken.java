package com.example.webservice.domains.notifications.models.entities;

import com.example.webservice.domains.common.models.entities.base.BaseEntity;
import com.example.webservice.domains.users.models.entities.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class FirebaseUserToken extends BaseEntity {
    @Column(nullable = false)
    @NotNull
    private String userToken;
    @OneToOne
    private User user;

    public FirebaseUserToken() {
    }

    public FirebaseUserToken(User user, String token) {
        this.user = user;
        this.userToken = token;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "FirebaseUserToken{" +
                "userToken='" + userToken + '\'' +
                ", user=" + user +
                "} " + super.toString();
    }
}
