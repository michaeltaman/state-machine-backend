package com.nanox.machinestate;

import com.nanox.machinestate.config.StateMachineConfig;
import com.nanox.machinestate.model.Flow;
import com.nanox.machinestate.model.State;
import com.nanox.machinestate.repository.FlowRepository;
import com.nanox.machinestate.repository.StateRepository;
import com.nanox.machinestate.service.FlowService;
import com.nanox.machinestate.service.StateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(StateMachineConfig.class)
public class StateMachineServiceTests {

    @Autowired
    private FlowService flowService;

    @Autowired
    private StateService stateService;

    @Autowired
    private StateMachine<String, String> stateMachine;

    @MockBean
    private FlowRepository flowRepository;

    @MockBean
    private StateRepository stateRepository;

    @Test
    void testStateWithMultipleFlows() {
        // Mock data
        Flow flow1 = new Flow();
        flow1.setName("FLOW1");
        Flow flow2 = new Flow();
        flow2.setName("FLOW2");

        State state1 = new State();
        state1.setName("STATE");
        state1.setFlow(flow1);

        State state2 = new State();
        state2.setName("STATE");
        state2.setFlow(flow2);

        when(flowRepository.findByName("FLOW1")).thenReturn(Optional.of(flow1));
        when(flowRepository.findByName("FLOW2")).thenReturn(Optional.of(flow2));
        when(stateRepository.findByFlow(flow1)).thenReturn(List.of(state1));
        when(stateRepository.findByFlow(flow2)).thenReturn(List.of(state2));

        // Assuming stateMachine is autowired or built in a similar manner
        StateMachine<String, String> stateMachine = buildStateMachine();

        // Test setting initial state for flow1
        assertDoesNotThrow(() -> {
            flowService.setInitialStateForFlow("FLOW1");
        });

        // Test setting initial state for flow2
        assertDoesNotThrow(() -> {
            flowService.setInitialStateForFlow("FLOW2");
        });

        // Verify interactions
        verify(flowRepository).findByName("FLOW1");
        verify(flowRepository).findByName("FLOW2");
        verify(stateRepository).findByFlow(flow1);
        verify(stateRepository).findByFlow(flow2);
    }

    private StateMachine<String, String> buildStateMachine() {
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

        try {
            builder.configureStates()
                    .withStates()
                    .initial("INITIAL_STATE")
                    .state("STATE1")
                    .end("END_STATE");

            builder.configureTransitions()
                    .withExternal()
                    .source("INITIAL_STATE").target("STATE1").event("E1")
                    .and()
                    .withExternal()
                    .source("STATE1").target("END_STATE").event("E2");

            return builder.build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
