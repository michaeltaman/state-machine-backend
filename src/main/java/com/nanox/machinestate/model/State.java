package com.nanox.machinestate.model;

import jakarta.persistence.*;

@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String value;

    private boolean initialState;

    @ManyToOne
    private Flow flow;

    public State() {
    }

    public State(String name, String value, Flow flow) {
        this.name = name;
        this.value = value;
        this.flow = flow;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Flow getFlow() {
        return flow;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    public boolean isInitialState() {
        return initialState;
    }

    public void setInitialState(boolean initialState) {
        this.initialState = initialState;
    }


    //It was necessary to run the following script:
    /*ALTER TABLE state
    ADD CONSTRAINT fk_state_flow FOREIGN KEY (flow_id)
    REFERENCES flow (id);*/

     /*ALTER TABLE state
    ADD initial_state BOOLEAN DEFAULT FALSE,
    ADD CONSTRAINT fk_state_flow FOREIGN KEY (flow_id)
    REFERENCES flow (id);*/

}
