package com.github.haseoo.courier.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Client")
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "clientId")
public class ClientModel extends  UserModel{
    private String emailAddress;
    @Column(nullable = false)
    private String phoneNumber;
}
