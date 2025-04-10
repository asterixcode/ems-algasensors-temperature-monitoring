package com.algaworks.algasensors.temperature.monitoring.domain.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sensor_monitoring")
public class SensorMonitoring {
  @Id
  @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "BIGINT"))
  private SensorId id;

  private Double lastTemperature;
  private OffsetDateTime updatedAt;
  private Boolean enabled;
}
