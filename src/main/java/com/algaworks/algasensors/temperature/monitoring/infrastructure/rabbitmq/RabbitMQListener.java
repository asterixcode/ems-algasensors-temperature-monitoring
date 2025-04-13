package com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import com.algaworks.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algaworks.algasensors.temperature.monitoring.domain.service.TemperatureMonitoringService;
import java.time.Duration;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

  private final TemperatureMonitoringService temperatureMonitoringService;

  public RabbitMQListener(TemperatureMonitoringService temperatureMonitoringService) {
    this.temperatureMonitoringService = temperatureMonitoringService;
  }

  @RabbitListener(
      queues = RabbitMQConfig.QUEUE,
      concurrency = "2-3") // Simulate a concurrency of 2 to 3
  public void listen(@Payload TemperatureLogData temperatureLogData) throws InterruptedException {
    temperatureMonitoringService.processTemperature(temperatureLogData);

    // Simulate a long-running task
    Thread.sleep(Duration.ofSeconds(5));
  }
}
