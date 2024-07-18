package com.nanox.machinestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nanox.machinestate.model.State;
import org.springframework.data.jpa.repository.Query;
import com.nanox.machinestate.model.Flow;

import java.util.List;
import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Long> {
    @Query("SELECT s FROM State s WHERE s.flow.id = :flowId")
    List<State> findByFlowId(Long flowId);

    List<State> findByFlow(Flow flow);

    Optional<State> findByName(String name);

    Optional<State> findByFlowAndName(Flow flow, String name);

    void deleteByFlow(Flow flow);
}
