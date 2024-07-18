package com.nanox.machinestate.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.nanox.machinestate.exception.ResourceNotFoundException;
import com.nanox.machinestate.model.Flow;
import com.nanox.machinestate.repository.FlowRepository;
import com.nanox.machinestate.exception.DuplicateResourceException;
import com.nanox.machinestate.repository.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FlowServiceTest {

//    @Test
//    public void shouldPass() {
//        assertTrue(true);
//    }

    @Mock
    private FlowRepository flowRepository;

    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private FlowService flowService;

    private Flow flow;

    @BeforeEach
    void setUp() {
        flow = new Flow();
    }

    @Test
    void testAddFlow() {
        when(flowRepository.save(any(Flow.class))).thenReturn(flow);
        Flow created = flowService.addFlow(new Flow());
        assertNotNull(created);
        // Additional assertions as necessary
    }

    @Test
    void testAddFlowWithDuplicateNameThrowsException() {
        // Given
        Flow newFlow = new Flow();
        newFlow.setName("existingFlowName");
        when(flowRepository.save(any(Flow.class))).thenThrow(DataIntegrityViolationException.class);

        // When & Then
        assertThrows(DuplicateResourceException.class, () -> flowService.addFlow(newFlow));

        // Verify that the save method was called
        verify(flowRepository).save(any(Flow.class));
    }

    @Test
    void testGetFlowById() {
        when(flowRepository.findById(anyLong())).thenReturn(Optional.of(flow));
        Flow found = flowService.getFlowById(1L);
        assertNotNull(found);
        // Additional assertions as necessary
    }

    @Test
    void testDeleteFlow() {
        long flowId = 1L; // The ID of the flow to delete
        Flow flow = new Flow();
        flow.setId(flowId);

        // Mock the repository to simulate the flow's existence
        when(flowRepository.findById(flowId)).thenReturn(Optional.of(flow));
        doNothing().when(flowRepository).deleteById(flowId);
        doNothing().when(stateRepository).deleteByFlow(flow);

        // Attempt to delete the flow, expecting no exceptions
        assertDoesNotThrow(() -> flowService.deleteFlow(flowId));

        // Verify the state delete operation was called on the state repository
        verify(stateRepository).deleteByFlow(flow);

        // Verify the delete operation was called on the flow repository
        verify(flowRepository).deleteById(flowId);
    }

    @Test
    void testDeleteFlowByName() {
        String flowName = "testFlow";
        Flow flow = new Flow();
        flow.setName(flowName);

        // Mock the repository to simulate the flow's existence
        when(flowRepository.findByName(flowName)).thenReturn(Optional.of(flow));
        doNothing().when(flowRepository).delete(flow);
        doNothing().when(stateRepository).deleteByFlow(flow);

        // Attempt to delete the flow by name, expecting no exceptions
        assertDoesNotThrow(() -> flowService.deleteFlowByName(flowName));

        // Verify the state delete operation was called on the state repository
        verify(stateRepository).deleteByFlow(flow);

        // Verify the delete operation was called on the flow repository
        verify(flowRepository).delete(flow);
    }

    @Test
    void testDeleteNonExistingFlow() {
        long flowId = 1L;

        // Mock the repository to simulate the flow's non-existence
        when(flowRepository.findById(flowId)).thenReturn(Optional.empty());

        // Attempt to delete the non-existing flow, expecting a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> flowService.deleteFlow(flowId));
    }

    @Test
    void testDeleteNonExistingFlowByName() {
        String flowName = "nonExistingFlow";

        // Mock the repository to simulate the flow's non-existence
        when(flowRepository.findByName(flowName)).thenReturn(Optional.empty());

        // Attempt to delete the non-existing flow by name, expecting a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> flowService.deleteFlowByName(flowName));
    }

}