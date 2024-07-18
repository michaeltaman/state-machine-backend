package com.nanox.machinestate.repository;

import com.nanox.machinestate.model.Flow;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FlowRepository extends JpaRepository<Flow, Long> {
    Optional<Flow> findByName(String name);
}
