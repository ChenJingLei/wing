package com.chinatel.caur2cdoauth2.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User implements UserDetails {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(nullable = false)
    private Boolean accountNonExpired = true;

    @Column(nullable = false)
    private Boolean accountNonLocked = true;

    @Column(nullable = false)
    private Boolean credentialsNonExpired = true;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;


    //急加载 会查询role表
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "id")})
    private Set<Role> roles;

    @Override
    public Set<Authority> getAuthorities() {
        Set<Authority> authoritieSet = new HashSet<>();
        for (Role role : roles) {
            authoritieSet.addAll(role.getAuthorities());
        }
        return authoritieSet;
    }

    public static User builder() {
        return new User();
    }

    public User withUsername(String username) {
        this.username = username;
        return this;
    }

    public User withPassword(String password) {
        this.password = password;
        return this;
    }

    public User withRole(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public boolean equals(Object rhs) {
        return rhs instanceof User && this.username.equals(((User) rhs).username);
    }

    public int hashCode() {
        return this.username.hashCode();
    }

}
