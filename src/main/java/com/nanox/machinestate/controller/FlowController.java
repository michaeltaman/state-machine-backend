package com.nanox.machinestate.controller;

import com.nanox.machinestate.model.Flow;
import com.nanox.machinestate.service.FlowService;
import com.nanox.machinestate.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flows")
@CrossOrigin(origins = "http://localhost:4200")
public class FlowController {

    private final FlowService flowService;

    @Autowired
    public FlowController(FlowService flowService) {
        this.flowService = flowService;
    }

    @PostMapping
    public ResponseEntity<Flow> createFlow(@RequestBody Flow flow) {
        Flow savedFlow = flowService.saveFlow(flow);
        return ResponseEntity.ok(savedFlow);
    }

    @GetMapping
    public ResponseEntity<List<Flow>> getAllFlow() {
        List<Flow> flows = flowService.getAllFlows();
        return ResponseEntity.ok(flows);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlow(@PathVariable Long id) {
        flowService.deleteFlow(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<Void> deleteFlowByName(@PathVariable String name) {
        flowService.deleteFlowByName(name);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleCustomDuplicateResourceException(DuplicateResourceException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

}


