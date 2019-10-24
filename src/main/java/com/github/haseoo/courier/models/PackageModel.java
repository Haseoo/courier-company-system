package com.github.haseoo.courier.models;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "package")
@Data
public class PackageModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(insertable = false, nullable = false)
    private Integer packageId;
    @Column(nullable = false)
    private String additionalInfo;
}
