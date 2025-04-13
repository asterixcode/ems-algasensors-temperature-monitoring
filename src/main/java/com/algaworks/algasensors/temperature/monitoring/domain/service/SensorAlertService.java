package com.algaworks.algasensors.temperature.monitoring.domain.service;

import com.algaworks.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SensorAlertService {

  private final SensorAlertRepository sensorAlertRepository;

  public SensorAlertService(SensorAlertRepository sensorAlertRepository) {
    this.sensorAlertRepository = sensorAlertRepository;
  }

  @Transactional
  public void handleAlert(TemperatureLogData temperatureLogData) {
    sensorAlertRepository
        .findById(new SensorId(temperatureLogData.sensorId()))
        .ifPresentOrElse(
            alert -> {
              if (alert.getMaxTemperature() != null
                  && temperatureLogData.value().compareTo(alert.getMaxTemperature()) >= 0) {
                log.info(
                    "Alert Max Temperature for sensor with id {} and value {}",
                    temperatureLogData.id(),
                    temperatureLogData.value());
              } else if (alert.getMinTemperature() != null
                  && temperatureLogData.value().compareTo(alert.getMinTemperature()) <= 0) {
                log.info(
                    "Alert Min Temperature for sensor with id {} and value {}",
                    temperatureLogData.id(),
                    temperatureLogData.value());
              } else {
                logIgnoredAlert(temperatureLogData);
              }
            },
            () -> logIgnoredAlert(temperatureLogData));
  }

  private static void logIgnoredAlert(TemperatureLogData temperatureLogData) {
    log.info(
        "Ignoring temperature alert for sensor with id {} and value {}",
        temperatureLogData.id(),
        temperatureLogData.value());
  }
}
