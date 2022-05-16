package com.blogads.repository.mysql;

import com.blogads.entity.mysql.Admin;
import com.blogads.vo.AdminRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByUsernameIgnoreCase(String userName);

    /**
     * fix bug with @CreatedBy is object
     * {@link com.blogads.configuration.AuditingConfiguration}
     *
     * @param userName
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    Optional<Admin> findByUsername(String userName);

    @Query(value = "select a " +
            "from Admin a " +
            "where a.role = :role " +
            "and (a.username like %:search% or a.fullName like %:search%)")
    Page<Admin> findByHost(AdminRole role, String search, Pageable pageable);

    Optional<Admin> findByVerifyToken(String token);
}