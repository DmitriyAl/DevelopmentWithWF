package io.albot.javaee7.chapter4.controller;

import io.albot.javaee7.chapter4.boundary.TheatreBox;
import io.albot.javaee7.chapter4.entity.Seat;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

/**
 * @author D. Albot
 * @date 07.10.2016
 */
@Named(value = "pollerBean")
public class Poller {
    @Inject
    private TheatreBox theatreBox;

    public boolean isPollingActive() {
        return areFreeSeatsAvailable();
    }

    private boolean areFreeSeatsAvailable() {
        final Optional<Seat> firstSeat = theatreBox.getSeats().stream()
                .filter(seat -> !seat.isBooked()).findFirst();
        return firstSeat.isPresent();
    }
}
