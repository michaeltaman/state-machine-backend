package com.nanox.machinestate.action;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public interface StateMachineAction extends Action<String, String> {
    @Override
    default void execute(StateContext<String, String> context) {
        performAction(context);
    }

    void performAction(StateContext<String, String> context);
}
