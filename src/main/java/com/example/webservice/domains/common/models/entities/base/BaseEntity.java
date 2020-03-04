package com.example.webservice.domains.common.models.entities.base;

import com.example.webservice.commons.utils.DateUtil;
import com.example.webservice.config.security.SecurityContext;
import com.example.webservice.domains.users.models.UserAuth;
import com.example.webservice.domains.users.models.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastUpdated;

    @OneToOne
    private User createdBy;

    @OneToOne
    private User updatedBy;


    @Column(name = "uuid_str")
    private String uuid;

    private boolean deleted;

    @PrePersist
    private void onBasePersist() {
        this.created = new Date();
        this.lastUpdated = new Date();
        if (getCurrentUser() != null)
            this.createdBy = new User(getCurrentUser());
        this.uuid = UUID.randomUUID().toString();
    }

    @PreUpdate
    private void onBaseUpdate() {
        this.lastUpdated = new Date();
        if (getCurrentUser() != null)
            this.updatedBy = new User(getCurrentUser());
    }

    @JsonIgnore
    public UserAuth getCurrentUser() {
        return SecurityContext.getCurrentUser();
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getReadableDate(Date date) {
        return DateUtil.getReadableDate(date);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getCreated() {
        return created;
    }


    public Date getLastUpdated() {
        return lastUpdated;
    }


    @JsonIgnore
    public User getCreatedBy() {
        return createdBy;
    }

    @JsonIgnore
    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
