package com.nerdysoft.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @Column(name = "description", length = 500)
    @NotNull
    @Pattern(regexp = ".{3,500}$")
    @SafeHtml
    private String description;

    @Column(name = "estimated_days")
    @NotNull
    @Range(max = 360L)
    private Integer estimatedDays;

    @Column(name = "is_completed")
    @NotNull
    private Boolean isCompleted;

    @Column(name = "created_by")
    @Pattern(regexp = ".{8,250}$")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @Column(name = "last_update_date")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate lastUpdateDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_tasks",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> users = new ArrayList<>();

    public Task() {
    }

    public Task(Integer id, String description, Integer estimatedDays, Boolean isCompleted,
                String createdBy, LocalDate lastUpdateDate) {
        this.id = id;
        this.description = description;
        this.estimatedDays = estimatedDays;
        this.isCompleted = isCompleted;
        this.createdBy = createdBy;
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

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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
