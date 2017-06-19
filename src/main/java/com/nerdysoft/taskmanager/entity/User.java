package com.nerdysoft.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.Hibernate;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@NamedEntityGraph(name = User.GRAPH_WITH_TASKS, attributeNodes = {@NamedAttributeNode("tasks")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String GRAPH_WITH_TASKS = "User.withTasks";

    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private static final String EMAIL_REGEX =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final java.util.regex.Pattern PASSWORD_PATTERN =
            java.util.regex.Pattern.compile(PASSWORD_REGEX);

    public static final java.util.regex.Pattern EMAIL_PATTERN =
            java.util.regex.Pattern.compile(EMAIL_REGEX);

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Pattern(regexp = "[a-zA-Z\\s']{3,250}$")
    @Column(name = "user_name")
    private String userName;

    @Pattern(regexp = "[a-zA-Z\\s']{3,250}$")
    @Column(name = "user_last_name")
    private String userLastName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = PASSWORD_REGEX)
    @Column(name = "password")
    private String password;

    @Pattern(regexp = EMAIL_REGEX)
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "registered")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate registered;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role")
    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "users_tasks",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "task_id")})
    private List<Task> tasks;

    public User() {
    }

    public User(Integer id, String userName, String userLastName, String password, String email,
                Boolean enabled, Boolean isAdmin, LocalDate registered, Set<Role> roles, List<Task> tasks) {
        this.id = id;
        this.userName = userName;
        this.userLastName = userLastName;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.isAdmin = isAdmin;
        this.registered = registered;
        this.roles = roles;
        this.tasks = tasks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        User that = (User) o;
        return null != getId() && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return (getId() == null) ? 0 : getId();
    }

}
