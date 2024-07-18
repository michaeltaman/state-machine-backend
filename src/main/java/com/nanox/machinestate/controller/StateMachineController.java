package com.nanox.machinestate.controller;

import com.nanox.machinestate.model.Flow;
import com.nanox.machinestate.model.State;
import com.nanox.machinestate.service.StateMachineService;
import com.nanox.machinestate.service.FlowService;
import com.nanox.machinestate.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/state-machine")
@CrossOrigin(origins = "http://localhost:4200")
public class StateMachineController {

    private final StateMachineService stateMachineService;
    private final FlowService flowService;
    private final StateService stateService;

    @Autowired
    public StateMachineController(StateMachineService stateMachineService, FlowService flowService, StateService stateService) {
        this.stateMachineService = stateMachineService;
        this.flowService = flowService;
        this.stateService = stateService;
    }

    @GetMapping("/trigger/{event}")
    public ResponseEntity<?> triggerEvent(@PathVariable String event) {
        boolean success = stateMachineService.sendEvent(event);
        if (success) {
            return ResponseEntity.ok().body("Event triggered: " + event);
        } else {
            return ResponseEntity.badRequest().body("Failed to trigger event: " + event);
        }
    }

    @GetMapping("/state")
    public ResponseEntity<?> getCurrentState() {
        String currentState = stateMachineService.getCurrentState();
        if (currentState == null) {
            return ResponseEntity.ok().body("Current State is not set");
        } else {
            return ResponseEntity.ok().body(currentState);
        }
    }

    @PostMapping("/state")
    public ResponseEntity<?> setState(@RequestBody StateRequest stateRequest) {
        Optional<State> state = stateService.findByName(stateRequest.getState());
        if (state.isPresent()) {
            boolean success = stateMachineService.setState(stateRequest.getState());
            if (success) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().body("Failed to set state");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid state");
        }
    }

    @PostMapping("/flow")
    public ResponseEntity<?> setFlow(@RequestBody FlowRequest flowRequest) {
        Optional<Flow> flow = flowService.findByName(flowRequest.getFlow());
        if (flow.isPresent()) {
            boolean success = stateMachineService.setInitialStateForFlow(flowRequest.getFlow());
            if (success) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().body("Failed to set flow");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid flow");
        }
    }

    @GetMapping("/flow")
    public ResponseEntity<?> getCurrentFlow() {
        Flow currentFlow = stateMachineService.getCurrentFlow();
        if (currentFlow != null) {
            return ResponseEntity.ok().body("Current Flow: " + currentFlow.getName());
        } else {
            return ResponseEntity.ok().body("Current flow is not set");
        }
    }

    static class StateRequest {
        private String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }

    static class FlowRequest {
        private String flow;

        public String getFlow() {
            return flow;
        }

        public void setFlow(String flow) {
            this.flow = flow;
        }
    }
}
