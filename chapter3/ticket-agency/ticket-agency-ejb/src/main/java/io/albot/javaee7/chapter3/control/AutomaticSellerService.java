package io.albot.javaee7.chapter3.control;

import io.albot.javaee7.chapter3.entity.Seat;
import io.albot.javaee7.chapter3.exception.NoSuchSeatException;
import io.albot.javaee7.chapter3.exception.SeatBookedException;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Collection;
import java.util.Optional;

/**
 * @author D. Albot
 * @date 05.10.2016
 */
@Stateless
public class AutomaticSellerService {
    private static final Logger log = Logger.getLogger(AutomaticSellerService.class);
    @EJB
    private TheatreBox theatreBox;
    @Resource
    private TimerService timerService;

    @Schedule(hour = "*", minute = "*", second = "*/10", persistent = false)
    public void automaticCustomer() throws NoSuchSeatException {
        final Optional<Seat> seatOptional = findFreeSeat();
        if (!seatOptional.isPresent()) {
            cancelTimers();
            log.info("Scheduler gone");
            return;
        }
        final Seat seat = seatOptional.get();
        try {
            theatreBox.buyTicket(seat.getId());
        } catch (SeatBookedException e) {
        }
        log.info("Somebody booked seat number " + seat.getId());
    }

    private Optional<Seat> findFreeSeat() {
        final Collection<Seat> list = theatreBox.getSeats();
        return list.stream().filter(seat -> !seat.isBooked()).findFirst();
    }

    private void cancelTimers() {
        timerService.getTimers().forEach(Timer::cancel);
    }
}
