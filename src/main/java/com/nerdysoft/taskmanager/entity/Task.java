package com.nerdysoft.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @Pattern(regexp = ".{0,250}$")
    @Column(name = "description")
    @SafeHtml
    private String description;

    @Range(max = 360L)
    @Column(name = "estimated_days")
    private Integer estimatedDays;

    @Column(name = "is_new_shared")
    private Boolean isNewShared;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @Pattern(regexp = ".{0,250}$")
    @Column(name = "created_by_user_with_email")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdByUserWithEmail;

    @Pattern(regexp = ".{0,250}$")
    @Column(name = "last_update_description")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastUpdateDescription;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "last_update_date")
    private LocalDate lastUpdateDate;

    public Task() {
    }

    public Task(Integer id, String description, Integer estimatedDays, Boolean isNewShared, Boolean isCompleted,
                String createdByUserWithEmail, String lastUpdateDescription, LocalDate lastUpdateDate) {
        this.id = id;
        this.description = description;
        this.estimatedDays = estimatedDays;
        this.isNewShared = isNewShared;
        this.isCompleted = isCompleted;
        this.createdByUserWithEmail = createdByUserWithEmail;
        this.lastUpdateDescription = lastUpdateDescription;
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEstimatedDays() {
        return estimatedDays;
    }

    public void setEstimatedDays(Integer estimatedDays) {
        this.estimatedDays = estimatedDays;
    }

    public Boolean getNewShared() {
        return isNewShared;
    }

    public void setNewShared(Boolean newShared) {
        isNewShared = newShared;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public String getCreatedByUserWithEmail() {
        return createdByUserWithEmail;
    }

    public void setCreatedByUserWithEmail(String createdByUserWithEmail) {
        this.createdByUserWithEmail = createdByUserWithEmail;
    }

    public String getLastUpdateDescription() {
        return lastUpdateDescription;
    }

    public void setLastUpdateDescription(String lastUpdateDescription) {
        this.lastUpdateDescription = lastUpdateDescription;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        Task that = (Task) o;
        return null != getId() && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return (getId() == null) ? 0 : getId();
    }

}
