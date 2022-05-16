package com.blogads.configuration;

import com.blogads.entity.mysql.Admin;
import com.blogads.repository.mysql.AdminRepository;
import com.blogads.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "createAuditorProvider")
public class AuditingConfiguration {

    @Autowired
    private AdminRepository adminRepository;

    @Bean
    public AuditorAware<Admin> createAuditorProvider() {
        return () -> adminRepository.findByUsername(SecurityUtils.getCurrentAdminOrElseThrow().getUsername());
    }
}