package com.blogads.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

/**
 * @author NhatPA
 * @since 11/03/2022 - 01:05
 */
@Getter
@AllArgsConstructor
public enum UserEnum {
    ADMIN_1("Hoàng Ngọc", "hoangngoc",
            "{bcrypt}$2a$10$H2Bs5lv7/pOurQwfBEWawuFrPxh6uX7L5yjPdb/A8KHGWv3Wziqeq",
            Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))
    ),

    ADMIN_2("NhậtPA", "admin",
            "{bcrypt}$2a$10$H2Bs5lv7/pOurQwfBEWawuFrPxh6uX7L5yjPdb/A8KHGWv3Wziqeq",
            Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))
    );

    private final String fullName;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> roles;
}
