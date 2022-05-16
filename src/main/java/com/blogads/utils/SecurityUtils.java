package com.blogads.utils;

import com.blogads.configuration.security.AdminDetail;
import com.blogads.entity.mysql.Admin;
import com.blogads.exception.BlogAdsException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtils {

    /**
     * Get current wallet login
     *
     * @return {@link Optional}
     */
    public static Optional<AdminDetail> getCurrentAdmin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication()).map(authentication -> {
            if (authentication.getPrincipal() instanceof AdminDetail) {
                return (AdminDetail) authentication.getPrincipal();
            }
            return null;
        });
    }

    public static void setCurrentAdmin(Admin admin) {
        AdminDetail adminDetail = new AdminDetail(admin);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                adminDetail,
                null,
                adminDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Get current wallet login
     *
     * @return {@link AdminDetail}
     * @throws com.blogads.exception.BlogAdsException if null
     */
    public static AdminDetail getCurrentAdminOrElseThrow() {
        return getCurrentAdmin()
                .orElseThrow(() -> new BlogAdsException(400, BlogAdsException.USERNAME_PASSWORD_INCORRECT));
    }
}
