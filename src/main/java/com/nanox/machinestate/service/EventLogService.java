package com.nanox.machinestate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nanox.machinestate.model.EventLog;
import com.nanox.machinestate.repository.EventLogRepository;

import java.util.List;

@Service
public class EventLogService {
    @Autowired
    private EventLogRepository eventLogRepository;

    public List<EventLog> getAllEventLogs() {
        return eventLogRepository.findAll();
    }

    // Additional methods to manage event logs
}
