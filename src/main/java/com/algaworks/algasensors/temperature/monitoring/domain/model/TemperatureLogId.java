package com.algaworks.algasensors.temperature.monitoring.domain.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class TemperatureLogId implements Serializable {
  private UUID value;

  public TemperatureLogId(UUID value) {
    this.value = value;
  }

  public TemperatureLogId(String value) {
    this.value = UUID.fromString(value);
  }

  public String toString() {
    return value.toString();
  }
}
