package com.chinatel.caur2cdsecurity.models;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false, unique = true)
    private String name;
    //懒加载 不会查询role表
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users;
    //急加载 会查询role表
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<Authority> authorities;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public void setAuthorities(List<Authority> authorities) {
        List<Authority> auths = new ArrayList<>();
        for (Authority auth : authorities) {
            if (!StringUtils.startsWithIgnoreCase(auth.getAuthority(), "ROLE_")) {
                auth.setName("ROLE_" + auth.getAuthority());
            }
            auths.add(auth);
        }
        this.authorities = auths;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof Role && this.name.equals(((Role) obj).name);
        }
    }

    public int hashCode() {
        return this.name.hashCode();
    }
}

