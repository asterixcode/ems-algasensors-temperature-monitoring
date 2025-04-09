package com.algaworks.algasensors.temperature.monitoring.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.OffsetDateTime;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorMonitoring {
  @Id
  @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "BIGINT"))
  private SensorId id;

  private Double lastTemperature;
  private OffsetDateTime updatedAt;
  private Boolean enabled;
}
