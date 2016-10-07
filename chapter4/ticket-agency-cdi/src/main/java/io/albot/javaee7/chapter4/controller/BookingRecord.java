package io.albot.javaee7.chapter4.controller;

import io.albot.javaee7.chapter4.entity.Seat;
import io.albot.javaee7.chapter4.util.NamedView;

import javax.enterprise.event.Observes;
import java.io.Serializable;

/**
 * @author D. Albot
 * @date 07.10.2016
 */
@NamedView
public class BookingRecord implements Serializable {
    private int bookedCount = 0;

    public int getBookedCount() {
        return bookedCount;
    }

    public void bookEvent(@Observes Seat bookedSeat) {
        bookedCount++;
    }
}
