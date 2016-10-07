package io.albot.javaee7.chapter4.boundary;

import io.albot.javaee7.chapter4.entity.Seat;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author D. Albot
 * @date 30.09.2016
 */
@Singleton
@Startup
@AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
public class TheatreBox  {
    private static final Logger log = Logger.getLogger(TheatreBox.class);
    private Map<Integer, Seat> seats;
    @Inject
    private Event<Seat> seatEvent;

    @PostConstruct
    public void setupTheatre() {
        seats = new HashMap<>();
        int id = 0;
        for (int i = 0; i < 5; i++) {
            final Seat seat = new Seat(++id, "Stalls", 40);
            addSeat(seat);
        }
        for (int i = 0; i < 5; i++) {
            final Seat seat = new Seat(++id, "Circle", 20);
            addSeat(seat);
        }
        for (int i = 0; i < 5; i++) {
            final Seat seat = new Seat(++id, "Balcony", 10);
            addSeat(seat);
        }
        log.info("Seat Map constructed.");
    }

    private void addSeat(Seat seat) {
        seats.put(seat.getId(), seat);
    }

    @Lock(LockType.READ)
    public Collection<Seat> getSeats() {
        return Collections.unmodifiableCollection(seats.values());
    }

    @Lock(LockType.READ)
    private Seat getSeat(int seatId) {
        final Seat seat = seats.get(seatId);
        return seat;
    }

    @Lock(LockType.READ)
    public int getSeatPrice(int seatId) {
        return getSeat(seatId).getPrice();
    }

    @Lock(LockType.WRITE)
    public void buyTicket(int seatId) {
        final Seat seat = getSeat(seatId);
        final Seat bookedSeat = seat.getBookedSeat();
        addSeat(bookedSeat);
        seatEvent.fire(bookedSeat);
    }
}

