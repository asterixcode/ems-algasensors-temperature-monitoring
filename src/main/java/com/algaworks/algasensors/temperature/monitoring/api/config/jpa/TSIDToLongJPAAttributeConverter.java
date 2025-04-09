package com.algaworks.algasensors.temperature.monitoring.api.config.jpa;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(
    autoApply = true // This will apply the converter automatically to all entities with TSID fields
    )
public class TSIDToLongJPAAttributeConverter implements AttributeConverter<TSID, Long> {

  @Override
  public Long convertToDatabaseColumn(TSID attribute) {
    return attribute.toLong();
  }

  @Override
  public TSID convertToEntityAttribute(Long dbData) {
    return TSID.from(dbData);
  }
}
