package com.nanox.machinestate.action;

import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component
public class StateMachineActionImpl implements StateMachineAction {

    @Override
    public void performAction(StateContext<String, String> context) {
        // Implement the action logic here
        System.out.println("Action performed!");
    }
}
