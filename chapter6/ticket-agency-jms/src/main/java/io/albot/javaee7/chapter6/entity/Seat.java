package io.albot.javaee7.chapter6.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author D. Albot
 * @date 08.10.2016
 */
@Entity
public class Seat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean booked;
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private SeatType seatType;

    public Seat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }
}
