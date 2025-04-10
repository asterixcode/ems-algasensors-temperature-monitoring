package com.algaworks.algasensors.temperature.monitoring.api.model;

import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorAlert;
import io.hypersistence.tsid.TSID;

public record SensorAlertOutput(TSID id, Double maxTemperature, Double minTemperature) {
  public static SensorAlertOutput from(SensorAlert sensorAlert) {
    return new SensorAlertOutput(
        sensorAlert.getId().getValue(),
        sensorAlert.getMaxTemperature(),
        sensorAlert.getMinTemperature());
  }
}
