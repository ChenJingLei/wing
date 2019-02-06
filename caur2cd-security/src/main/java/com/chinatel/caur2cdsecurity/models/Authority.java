package com.chinatel.caur2cdsecurity.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自动生成
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String url;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY)//懒加载   快速查询 不会查询role表
    @JoinTable(
            name = "ROLE_Auth",
            joinColumns = {@JoinColumn(name = "auth_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "id")})
    private Set<Role> roles;

    public Authority() {
    }

    public Authority(String name, String url) {
        if (!StringUtils.startsWithIgnoreCase(name, "ROLE_")) {
            name = "ROLE_" + name;
        }
        this.name = name;
        this.url = url;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public void setName(String name) {
        if (!StringUtils.startsWithIgnoreCase(name, "ROLE_")) {
            name = "ROLE_" + name;
        }
        this.name = name;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof Authority && this.name.equals(((Authority) obj).name) && this.url.equals((((Authority) obj).url));
        }
    }

    public int hashCode() {
        return this.url.hashCode();
    }

}
