package com.nanox.machinestate.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Flow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // This enforces the uniqueness constraint
    private String name;

    @OneToMany(mappedBy = "flow", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<State> states;

    // Constructors, Getters, and Setters
    public Flow() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*
    //It was necessary to run the following script:
    ALTER TABLE flow
    ADD CONSTRAINT unique_flow_name UNIQUE (name);

    * */
}