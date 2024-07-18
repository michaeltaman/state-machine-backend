package com.nanox.machinestate.service;

import com.nanox.machinestate.config.CustomStateMachineContext;
import com.nanox.machinestate.model.Flow;
import com.nanox.machinestate.model.State;
import com.nanox.machinestate.repository.FlowRepository;
import com.nanox.machinestate.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StateMachineService {

    private final StateMachine<String, String> stateMachine;

    @Autowired
    private FlowRepository flowRepository;

    @Autowired
    private StateRepository stateRepository;

    private Flow currentFlow;

    @Autowired
    public StateMachineService(StateMachine<String, String> stateMachine) {
        this.stateMachine = stateMachine;
    }

    public boolean setInitialStateForFlow(String flowName) {
        Optional<Flow> optionalFlow = flowRepository.findByName(flowName);
        if (optionalFlow.isPresent()) {
            Flow flow = optionalFlow.get();
            Optional<State> initialState = stateRepository.findByFlow(flow).stream().findFirst();
            if (initialState.isPresent()) {
                stateMachine.stop();
                stateMachine.getStateMachineAccessor()
                        .doWithAllRegions(access -> access.resetStateMachine(new DefaultStateMachineContext<>(initialState.get().getName(), null, null, null)));
                stateMachine.start();
                currentFlow = flow;
                return true;
            }
        }
        return false;
    }


    public boolean setState(String stateName) {
        Optional<State> stateOptional = stateRepository.findByName(stateName);
        if (stateOptional.isPresent()) {
            State state = stateOptional.get();
            stateMachine.stop();
            stateMachine.getStateMachineAccessor()
                    .doWithAllRegions(access -> access.resetStateMachine(
                            new CustomStateMachineContext<>(state.getName(), null, state.getId())));
            stateMachine.getExtendedState().getVariables().put("stateId", state.getId());
            stateMachine.start();
            currentFlow = state.getFlow();
            return true;
        }
        return false;
    }

    public Flow getCurrentFlow() {
        return currentFlow;
    }


    public String getCurrentState() {
        if (stateMachine.getState() != null) {
            Long stateId = stateMachine.getExtendedState().get("stateId", Long.class);
            if (stateId != null) {
                Optional<State> stateOptional = stateRepository.findById(stateId);
                if (stateOptional.isPresent()) {
                    State state = stateOptional.get();
                    /////return "State ID: " + state.getId() + ", State Name: " + state.getName();
                    return state.getName();
                }
            }
        }
        return null;
    }





    public boolean sendEvent(String event) {
        try {
            return stateMachine.sendEvent(event);
        } catch (Exception e) {
            return false;
        }
    }
}
