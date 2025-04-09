package com.algaworks.algasensors.temperature.monitoring.domain.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "TEMPERATURE_LOG")
public class TemperatureLog {

  @Id
  @AttributeOverride(name = "value", column = @Column(name = "uuid"))
  private TemperatureLogId id;

  @Column(name = "\"value\"")
  private Double value;

  private OffsetDateTime registeredAt;

  @Embedded
  @AttributeOverride(
      name = "value",
      column = @Column(name = "sensor_id", columnDefinition = "BIGINT"))
  private SensorId sensorId;
}
