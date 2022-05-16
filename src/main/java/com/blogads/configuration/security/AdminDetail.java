package com.blogads.configuration.security;

import com.blogads.entity.mysql.Admin;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class AdminDetail implements UserDetails {

    private int adminId;

    private String username;

    private String password;

    private String fullName;

    private boolean enable;

    private String address;

    private List<GrantedAuthority> roles;

    public AdminDetail(Admin admin) {
        adminId = admin.getAdminId();
        username = admin.getUsername();
        password = admin.getPassword();
        fullName = admin.getFullName();
        enable = admin.getEnable();
        address = admin.getAddress();
        roles = Collections.singletonList(new SimpleGrantedAuthority(admin.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enable;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enable;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enable;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}
