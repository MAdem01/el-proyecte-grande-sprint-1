package com.codecool.codekickfc.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Role {
    @Id
    private int id;
    @Column
    private String type;

    public Role() {
    }
}
