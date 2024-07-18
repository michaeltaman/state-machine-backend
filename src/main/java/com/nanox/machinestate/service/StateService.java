package com.nanox.machinestate.service;

import com.nanox.machinestate.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nanox.machinestate.model.State;
import com.nanox.machinestate.model.Flow;
import com.nanox.machinestate.repository.StateRepository;
import java.util.List;
import java.util.Optional;


@Service
public class StateService {
    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private FlowService flowService;

    @Transactional
    public State createState(String name, Long flowId, String value) {
        // Fetch the associated flow
        Flow flow = flowService.getFlowById(flowId);

        // Check if a state with the same name already exists for the given flow
        Optional<State> existingState = stateRepository.findByFlowAndName(flow, name);
        if (existingState.isPresent()) {
            throw new DuplicateResourceException("A state with the name '" + name + "' already exists in the flow '" + flow.getName() + "'.");
        }

        // Create and save the new state with the provided value or an empty string if not provided
        State newState = new State(name, value, flow);
        return stateRepository.save(newState);
    }

    @Transactional(readOnly = true)
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<State> getStatesByFlow(Flow flow) {
        return stateRepository.findByFlow(flow);
    }

    @Transactional(readOnly = true)
    public State getStateById(Long id) {
        return stateRepository.findById(id).orElseThrow(() -> new RuntimeException("State not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Optional<State> findByName(String name) {
        return stateRepository.findByName(name);
    }

    @Transactional
    public State updateStateValue(Long id, String newValue) {
        State state = getStateById(id);
        state.setValue(newValue);
        return stateRepository.save(state);
    }

    @Transactional(readOnly = true)
    public List<State> getStatesByFlowId(Long flowId) {
        // This assumes you have a method in StateRepository to find states by flow ID
        return stateRepository.findByFlowId(flowId);
    }

    @Transactional
    public void deleteStateFlowAssociation(Long stateId, Long flowId) {
        Optional<State> optionalState = stateRepository.findById(stateId);
        if (optionalState.isPresent()) {
            State state = optionalState.get();
            if (state.getFlow().getId().equals(flowId)) {
                stateRepository.delete(state);
            } else {
                throw new IllegalArgumentException("State does not belong to the provided flow ID");
            }
        } else {
            throw new IllegalArgumentException("State not found");
        }
    }

    public void saveState(State currentState) {
        stateRepository.save(currentState);
    }
}