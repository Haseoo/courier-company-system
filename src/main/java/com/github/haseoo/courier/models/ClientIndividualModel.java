package com.github.haseoo.courier.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "ClientIndividual")
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "clientId")
public class ClientIndividualModel extends ClientModel {
    @Column(nullable = false, columnDefinition="TEXT")
    private String name;
    @Column(nullable = false, columnDefinition="TEXT")
    private String surname;
    @Column(nullable = false)
    private String pesel;
}
