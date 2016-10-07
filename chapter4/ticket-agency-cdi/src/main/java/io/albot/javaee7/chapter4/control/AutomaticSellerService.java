package io.albot.javaee7.chapter4.control;

import io.albot.javaee7.chapter4.boundary.TheatreBox;
import io.albot.javaee7.chapter4.entity.Seat;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

/**
 * @author D. Albot
 * @date 07.10.2016
 */
@Stateless
public class AutomaticSellerService {
    @Inject
    private Logger logger;
    @Inject
    private TheatreBox theatreBox;
    @Resource
    private TimerService timerService;

    @Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)
    public void automaticCustomer() {
        final Optional<Seat> seatOptional = findFreeSeat();

        if (!seatOptional.isPresent()) {
            cancelTimers();
            logger.info("Scheduler gone!");
            return;
        }
        final Seat seat = seatOptional.get();
        theatreBox.buyTicket(seat.getId());
        logger.info("Somebody just booked seat number " + seat.getId());
    }

    private Optional<Seat> findFreeSeat() {
        final Collection<Seat> list = theatreBox.getSeats();
        return list.stream().filter(seat -> !seat.isBooked()).findAny();
    }

    private void cancelTimers() {
        timerService.getTimers().forEach(Timer::cancel);
    }
}
