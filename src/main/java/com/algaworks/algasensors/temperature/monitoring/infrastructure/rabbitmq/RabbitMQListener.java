package com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import com.algaworks.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algaworks.algasensors.temperature.monitoring.domain.service.TemperatureMonitoringService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

  private final TemperatureMonitoringService temperatureMonitoringService;

  public RabbitMQListener(TemperatureMonitoringService temperatureMonitoringService) {
    this.temperatureMonitoringService = temperatureMonitoringService;
  }

  @RabbitListener(queues = RabbitMQConfig.QUEUE)
  public void listen(@Payload TemperatureLogData temperatureLogData) {
    temperatureMonitoringService.processTemperature(temperatureLogData);
  }
}
