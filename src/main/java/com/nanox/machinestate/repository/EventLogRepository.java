package com.nanox.machinestate.repository;

import com.nanox.machinestate.model.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {
    // Custom queries if needed
}
