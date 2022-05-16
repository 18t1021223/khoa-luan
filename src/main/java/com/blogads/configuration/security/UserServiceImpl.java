package com.blogads.configuration.security;

import com.blogads.entity.mysql.Admin;
import com.blogads.exception.BlogAdsException;
import com.blogads.repository.mysql.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static com.blogads.exception.BlogAdsException.USERNAME_PASSWORD_INCORRECT;

@Component
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new BlogAdsException(400, USERNAME_PASSWORD_INCORRECT));
        return new AdminDetail(admin);
    }
}
