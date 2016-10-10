package io.albot.javaee7.chapter6.entity;

/**
 * @author D. Albot
 * @date 08.10.2016
 */
public enum SeatPosition {
    ORCHESTRA("Orchestra", "orchestra"),
    BOX("Box", "box"),
    BALCONY("Balcony", "balcony");

    private final String label;
    private final String dbRepresentation;

    SeatPosition(String label, String dbRepresentation) {
        this.label = label;
        this.dbRepresentation = dbRepresentation;
    }

    public String getLabel() {
        return label;
    }

    public String getDbRepresentation() {
        return dbRepresentation;
    }
}
