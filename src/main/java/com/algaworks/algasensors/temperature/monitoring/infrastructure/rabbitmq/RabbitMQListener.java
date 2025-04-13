package com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import com.algaworks.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import io.hypersistence.tsid.TSID;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

  private static final Logger log = LoggerFactory.getLogger(RabbitMQListener.class);

  @RabbitListener(queues = RabbitMQConfig.QUEUE)
  public void listen(
      @Payload TemperatureLogData temperatureLogData, @Headers Map<String, Object> headers) {
    TSID sensorId = temperatureLogData.sensorId();
    Double temperature = temperatureLogData.value();
    log.info("Received temperature data of sensor {} with value {}", sensorId, temperature);
    log.info("Headers: {}", headers);
  }
}
