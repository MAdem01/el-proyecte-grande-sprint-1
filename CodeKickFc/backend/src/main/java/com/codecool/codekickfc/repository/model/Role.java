package com.codecool.codekickfc.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Role {

    @Id
    private int id;
    @Column
    private String type;

    public Role(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public Role() {
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
