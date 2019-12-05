package com.github.haseoo.courier.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "ClientCompany")
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "clientId")
public class ClientCompanyModel extends ClientModel {
    @Column(nullable = false, columnDefinition="TEXT")
    private String companyName;
    @Column(nullable = false)
    private String nip;
    @Column(nullable = false, columnDefinition="TEXT")
    private String representativeName;
    @Column(nullable = false, columnDefinition="TEXT")
    private String representativeSurname;
    @Column(nullable = false)
    private String representativeEmailAddress;
    @Column(nullable = false)
    private String representativePhoneNumber;
}
