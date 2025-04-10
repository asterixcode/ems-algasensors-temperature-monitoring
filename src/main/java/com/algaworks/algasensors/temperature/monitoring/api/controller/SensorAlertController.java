package com.algaworks.algasensors.temperature.monitoring.api.controller;

import com.algaworks.algasensors.temperature.monitoring.api.model.SensorAlertInput;
import com.algaworks.algasensors.temperature.monitoring.api.model.SensorAlertOutput;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors/{sensorId}/alert")
public class SensorAlertController {

  private final SensorAlertRepository sensorAlertRepository;

  public SensorAlertController(SensorAlertRepository sensorAlertRepository) {
    this.sensorAlertRepository = sensorAlertRepository;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public SensorAlertOutput getAlert(@PathVariable String sensorId) {
    SensorAlert sensorAlert =
        sensorAlertRepository
            .findById(new SensorId(sensorId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    return SensorAlertOutput.from(sensorAlert);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public SensorAlertOutput updateAlert(
      @PathVariable String sensorId, @RequestBody SensorAlertInput sensorAlertInput) {
    SensorAlert sensorAlert = findByIdOrDefault(sensorId);
    if (sensorAlertInput != null) {
      sensorAlert.setMaxTemperature(sensorAlertInput.maxTemperature());
      sensorAlert.setMinTemperature(sensorAlertInput.minTemperature());
    }
    return SensorAlertOutput.from(sensorAlertRepository.save(sensorAlert));
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAlert(@PathVariable String sensorId) {
    SensorAlert sensorAlert =
        sensorAlertRepository
            .findById(new SensorId(sensorId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    sensorAlertRepository.delete(sensorAlert);
  }

  private SensorAlert findByIdOrDefault(String sensorId) {
    return sensorAlertRepository
        .findById(new SensorId(sensorId))
        .orElse(
            SensorAlert.builder()
                .id(new SensorId(sensorId))
                .maxTemperature(0.0)
                .minTemperature(0.0)
                .build());
  }
}
