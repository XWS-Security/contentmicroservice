package org.nistagram.contentmicroservice.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "gram_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User implements UserDetails {
    @Transient
    private final String administrationRole = "NISTAGRAM_USER_ROLE";

    @Id
    @SequenceGenerator(name = "user_sequence_generator", sequenceName = "user_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "enabled")
    private boolean enabled = false;

    @Column(name = "lastPasswordResetDate")
    private Date lastPasswordResetDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    protected User() {
        this.roles = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getAdministrationRole() {
        return this.administrationRole;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGrantedAuthorities();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return "";
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    private List<GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : this.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled &&
                Objects.equals(administrationRole, user.administrationRole) &&
                Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(lastPasswordResetDate, user.lastPasswordResetDate) &&
                Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(administrationRole, id, username, enabled, lastPasswordResetDate, roles);
    }
}
