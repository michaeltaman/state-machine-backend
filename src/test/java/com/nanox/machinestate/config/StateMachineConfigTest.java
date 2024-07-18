package com.nanox.machinestate.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StateMachineConfigTest {

    @Autowired
    private StateMachine<String, String> stateMachine;


    /*@BeforeEach
    public void setup() {
        // Assuming a method to reset or recreate the state machine
        stateMachine.stop();  // Stop the current state machine instance
        stateMachine.start(); // Restart to reset its state
    }*/


    @BeforeEach
    public void setUp() {
        stateMachine.addStateListener(stateChangeListener());
        stateMachine.start();
    }

    @AfterEach
    public void tearDown() {
        stateMachine.removeStateListener(stateChangeListener());
        stateMachine.stop();
    }


    private StateMachineListener<String, String> stateChangeListener() {
        return new StateMachineListenerAdapter<String, String>() {
            @Override
            public void stateChanged(State<String, String> from, State<String, String> to) {
                System.out.println("State changed from " + (from != null ? from.getId() : "null") + " to " + to.getId());
            }
        };
    }


    @Test
    public void testInitialState() {
        // Check if the state machine is not null
        assertNotNull(stateMachine, "State machine should not be null");

        // Check the initial state of the state machine
        State<String, String> initialState = stateMachine.getState();
        assertEquals("INITIAL_STATE", initialState.getId(), "The initial state should be 'INITIAL_STATE'");
    }


    @Test
    public void testTransitionFromInitialToState1() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        stateMachine.addStateListener(new StateMachineListenerAdapter<String, String>() {
            @Override
            public void stateChanged(State<String, String> from, State<String, String> to) {
                if ("STATE1".equals(to.getId())) {
                    latch.countDown();
                }
            }
        });

        stateMachine.sendEvent("E1");
        boolean completed = latch.await(1, TimeUnit.SECONDS);
        assertTrue(completed, "State transition did not complete in time");
        assertEquals("STATE1", stateMachine.getState().getId(), "State machine should transition to 'STATE1'");
    }


    @Test
    public void testTransitionFromState1ToEndState() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        stateMachine.addStateListener(new StateMachineListenerAdapter<String, String>() {
            @Override
            public void stateChanged(State<String, String> from, State<String, String> to) {
                if ("END_STATE".equals(to.getId())) {
                    latch.countDown();
                }
            }
        });

        stateMachine.sendEvent("E1");
        Thread.sleep(200); // Ensure 'E1' is processed
        stateMachine.sendEvent("E2");

        boolean completed = latch.await(1, TimeUnit.SECONDS);
        assertTrue(completed, "State transition to 'END_STATE' did not complete in time");

        assertEquals("END_STATE", stateMachine.getState().getId(), "State machine should transition to 'END_STATE'");
    }
}
