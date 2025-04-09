package com.algaworks.algasensors.temperature.monitoring.api.model;

import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import io.hypersistence.tsid.TSID;
import java.time.OffsetDateTime;

public record SensorMonitoringOutput(
    TSID id, Double lastTemperature, OffsetDateTime updatedAt, Boolean enabled) {
  public static SensorMonitoringOutput from(SensorMonitoring sensorMonitoring) {
    return new SensorMonitoringOutput(
        sensorMonitoring.getId().getValue(),
        sensorMonitoring.getLastTemperature(),
        sensorMonitoring.getUpdatedAt(),
        sensorMonitoring.getEnabled());
  }
}
