package com.algaworks.algasensors.temperature.monitoring.domain.service;

import com.algaworks.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algaworks.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.algaworks.algasensors.temperature.monitoring.domain.model.TemperatureLogId;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import jakarta.transaction.Transactional;
import java.time.OffsetDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TemperatureMonitoringService {

  private final SensorMonitoringRepository sensorMonitoringRepository;
  private final TemperatureLogRepository temperatureLogRepository;

  public TemperatureMonitoringService(
      SensorMonitoringRepository sensorMonitoringRepository,
      TemperatureLogRepository temperatureLogRepository) {
    this.sensorMonitoringRepository = sensorMonitoringRepository;
    this.temperatureLogRepository = temperatureLogRepository;
  }

  @Transactional
  public void processTemperature(TemperatureLogData temperatureLogData) {
    sensorMonitoringRepository
        .findById(new SensorId(temperatureLogData.sensorId()))
        .ifPresentOrElse(
            sensorMonitoring -> handleSensorMonitoring(sensorMonitoring, temperatureLogData),
            () -> logIgnoredTemperature(temperatureLogData));
  }

  private void handleSensorMonitoring(
      SensorMonitoring sensorMonitoring, TemperatureLogData temperatureLogData) {
    if (sensorMonitoring.isEnabled()) {
      sensorMonitoring.setLastTemperature(temperatureLogData.value());
      sensorMonitoring.setUpdatedAt(OffsetDateTime.now());
      sensorMonitoringRepository.save(sensorMonitoring);

      TemperatureLog temperatureLog =
          TemperatureLog.builder()
              .id(new TemperatureLogId(temperatureLogData.id()))
              .registeredAt(temperatureLogData.registeredAt())
              .sensorId(new SensorId(temperatureLogData.sensorId()))
              .value(temperatureLogData.value())
              .build();

      temperatureLogRepository.save(temperatureLog);
      log.info(
          "Processed temperature log with id {} and value {}",
          temperatureLogData.id(),
          temperatureLogData.value());
    } else {
      logIgnoredTemperature(temperatureLogData);
    }
  }

  private void logIgnoredTemperature(TemperatureLogData temperatureLogData) {
    log.info(
        "Ignoring temperature log with id {} and value {}",
        temperatureLogData.id(),
        temperatureLogData.value());
  }
}
