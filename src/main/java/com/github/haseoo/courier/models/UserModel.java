package com.github.haseoo.courier.models;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.JOINED;

@Entity
@Data
@Table(name = "CompanyUser")
@Inheritance(strategy = JOINED)
public class UserModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false, insertable = false)
    private Long id;
    @Column
    private String userName;
    @Column
    private char[] password;
    @Column(nullable = false)
    private Boolean active;
}
