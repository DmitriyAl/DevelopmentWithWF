package io.albot.javaee7.chapter3.control;

import io.albot.javaee7.chapter3.entity.Seat;
import io.albot.javaee7.chapter3.exception.NoSuchSeatException;
import io.albot.javaee7.chapter3.exception.SeatBookedException;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.*;
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
public class TheatreBox {
    private static final Logger log = Logger.getLogger(TheatreBox.class);
    private Map<Integer, Seat> seats;
//    @Resource
//    private TimerService timerService;
//    private static final long DURATION = TimeUnit.SECONDS.toMillis(6);

    @PostConstruct
    public void setupTheatre() {
        seats = new HashMap<>();
        int id = 0;
        for (int i = 0; i < 5; i++) {
            addSeat(new Seat(++id, "Stalls", 40));
            addSeat(new Seat(++id, "Circle", 20));
            addSeat(new Seat(++id, "Balcony", 10));
        }
        log.info("Seat map constructed");
//        createTimer();
    }

    private void addSeat(Seat seat) {
        seats.put(seat.getId(), seat);
    }

//    public void createTimer() {
//        timerService.createSingleActionTimer(DURATION, new TimerConfig());
//    }
//
//    @Timeout
//    public void timeout(Timer timer) {
//        log.info("Re-building Theatre Map.");
//        setupTheatre();
//    }

    @Lock(LockType.READ)
    public Collection<Seat> getSeats() {
        return Collections.unmodifiableCollection(seats.values());
    }

    @Lock(LockType.READ)
    private Seat getSeat(int seatId) throws NoSuchSeatException {
        final Seat seat = seats.get(seatId);
        if (seat == null) {
            throw new NoSuchSeatException("Seat " + seatId + " does not exist!");
        }
        return seat;
    }

    @Lock(LockType.READ)
    public int getSeatPrice(int seatId) throws NoSuchSeatException {
        return getSeat(seatId).getPrice();
    }

    @Lock(LockType.WRITE)
    public void buyTicket(int seatId) throws SeatBookedException, NoSuchSeatException {
        final Seat seat = getSeat(seatId);
        if (seat.isBooked()) {
            throw new SeatBookedException("Seat " + seatId + " already booked!");
        }
        addSeat(seat.getBookedSeat());
    }
}

