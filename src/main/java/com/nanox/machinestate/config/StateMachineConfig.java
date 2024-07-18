package com.nanox.machinestate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.builders.StateMachineConfigBuilder;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.action.Action;

@Configuration
@EnableStateMachine
public class StateMachineConfig {

    @Bean
    public StateMachine<String, String> stateMachine() throws Exception {
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial("INITIAL_STATE")
                .state("STATE1")
                .end("END_STATE");

        builder.configureTransitions()
                .withExternal()
                .source("INITIAL_STATE").target("STATE1").event("E1").action(debugAction())
                .and()
                .withExternal()
                .source("STATE1").target("END_STATE").event("E2").action(debugAction());

        builder.configureConfiguration()
                .withConfiguration()
                .autoStartup(true)
                .listener(loggingListener());

        return builder.build();
    }

    @Bean
    public StateMachineListener<String, String> loggingListener() {
        return new StateMachineListenerAdapter<String, String>() {
            @Override
            public void stateChanged(State<String, String> from, State<String, String> to) {
                System.out.println("Transitioned from " + (from != null ? from.getId() : "null") + " to " + to.getId());
            }
        };
    }

    @Bean
    public Action<String, String> debugAction() {
        return context -> {
            System.out.println("Event Triggered: " + context.getEvent());
            System.out.println("Transition from: " + context.getSource().getId() + " to " + context.getTarget().getId());
        };
    }
}
