package com.blogads.entity.mysql;

import com.blogads.vo.AdminRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "verifyToken")
})
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    @Column(length = 50)
    private String username;

    @Column(length = 150)
    private String password;

    @Column(length = 150)
    private String fullName;

    private String address;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private AdminRole role;

    private Boolean enable;

    @Column(length = 150)
    private String verifyToken;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date expired;
}
