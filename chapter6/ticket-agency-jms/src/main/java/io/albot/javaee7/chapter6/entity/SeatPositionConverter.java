package io.albot.javaee7.chapter6.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author D. Albot
 * @date 08.10.2016
 */
@Converter(autoApply = true)
public class SeatPositionConverter implements AttributeConverter<SeatPosition, String> {
    public String convertToDatabaseColumn(SeatPosition attribute) {
        return attribute.getDbRepresentation();
    }

    public SeatPosition convertToEntityAttribute(String dbData) {
        for (SeatPosition seatPosition : SeatPosition.values()) {
            if (dbData.equals(seatPosition.getDbRepresentation())) {
                return seatPosition;
            }
        }
        throw new IllegalArgumentException("Unknown attribute value " + dbData);
    }
}
