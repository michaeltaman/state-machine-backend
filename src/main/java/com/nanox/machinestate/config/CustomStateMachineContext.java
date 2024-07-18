package com.nanox.machinestate.config;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.support.DefaultStateMachineContext;

public class CustomStateMachineContext<S, E> extends DefaultStateMachineContext<S, E> {
    private final Long stateId;

    public CustomStateMachineContext(S state, E event, Long stateId) {
        super(state, event, null, null, null, null);
        this.stateId = stateId;
    }

    public Long getStateId() {
        return stateId;
    }
}
