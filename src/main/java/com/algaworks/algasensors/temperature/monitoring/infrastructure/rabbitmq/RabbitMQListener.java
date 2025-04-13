package com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import com.algaworks.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algaworks.algasensors.temperature.monitoring.domain.service.SensorAlertService;
import com.algaworks.algasensors.temperature.monitoring.domain.service.TemperatureMonitoringService;
import java.time.Duration;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

  private final TemperatureMonitoringService temperatureMonitoringService;
  private final SensorAlertService sensorAlertService;

  public RabbitMQListener(
      TemperatureMonitoringService temperatureMonitoringService,
      SensorAlertService sensorAlertService) {
    this.temperatureMonitoringService = temperatureMonitoringService;
    this.sensorAlertService = sensorAlertService;
  }

  @RabbitListener(
      queues = RabbitMQConfig.QUEUE_PROCESS_TEMPERATURE,
      concurrency = "2-3") // Simulate a concurrency of 2 to 3
  public void handleProcessTemperature(@Payload TemperatureLogData temperatureLogData)
      throws InterruptedException {
    temperatureMonitoringService.processTemperature(temperatureLogData);

    // Simulate a long-running task
    Thread.sleep(Duration.ofSeconds(5));
  }

  @RabbitListener(queues = RabbitMQConfig.QUEUE_ALERT, concurrency = "2-3")
  public void handleAlert(@Payload TemperatureLogData temperatureLogData)
      throws InterruptedException {
    sensorAlertService.handleAlert(temperatureLogData);

    // Simulate a long-running task
    Thread.sleep(Duration.ofSeconds(5));
  }
}
