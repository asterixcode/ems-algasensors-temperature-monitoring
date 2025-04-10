package com.algaworks.algasensors.temperature.monitoring.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sensor_alert")
public class SensorAlert {

  @Id
  @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "BIGINT"))
  private SensorId id;

  private Double maxTemperature;
  private Double minTemperature;
}
