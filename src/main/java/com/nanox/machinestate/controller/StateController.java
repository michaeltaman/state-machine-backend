package com.nanox.machinestate.controller;

import com.nanox.machinestate.controller.dto.CreateStateRequest;
import com.nanox.machinestate.controller.dto.StateUpdateRequest;
import com.nanox.machinestate.controller.dto.StateUpdateResponse;
import com.nanox.machinestate.model.State;
import com.nanox.machinestate.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/states")
@CrossOrigin(origins = "http://localhost:4200")
public class StateController {

    @Autowired
    private StateService stateService;

    @PostMapping
    public ResponseEntity<State> createState(@RequestBody CreateStateRequest request) {
        State newState = stateService.createState(request.getName(), request.getFlowId(), request.getValue());
        return ResponseEntity.ok(newState);
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllStates() {
        List<State> states = stateService.getAllStates();
        List<String> stateNames = states.stream().map(State::getName).collect(Collectors.toList());
        return ResponseEntity.ok(stateNames);
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> getStateById(@PathVariable Long id) {
        State state = stateService.getStateById(id);
        return ResponseEntity.ok(state);
    }

    @GetMapping("/flow/{flowId}")
    public ResponseEntity<List<State>> getStatesByFlowId(@PathVariable Long flowId) {
        List<State> states = stateService.getStatesByFlowId(flowId);
        return ResponseEntity.ok(states);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateUpdateResponse> updateStateValue(@PathVariable Long id, @RequestBody StateUpdateRequest request) {
        State existingState = stateService.getStateById(id);
        String previousValue = existingState.getValue();
        State updatedState = stateService.updateStateValue(id, request.getValue());
        StateUpdateResponse response = new StateUpdateResponse(previousValue, updatedState.getValue());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{stateId}")
    public ResponseEntity<Void> deleteState(@PathVariable Long stateId, @RequestParam Long flowId) {
        stateService.deleteStateFlowAssociation(stateId, flowId);
        return ResponseEntity.noContent().build();
    }
}