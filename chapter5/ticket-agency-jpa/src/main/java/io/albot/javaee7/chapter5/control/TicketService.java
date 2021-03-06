package io.albot.javaee7.chapter5.control;

import io.albot.javaee7.chapter5.entity.Seat;
import io.albot.javaee7.chapter5.entity.SeatType;
import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.List;

/**
 * @author D. Albot
 * @date 08.10.2016
 */
@Stateless
public class TicketService {
    @Inject
    private Logger log;
    @Inject
    private Event<SeatType> seatTypeEventSrc;
    @Inject
    private Event<Seat> seatEventSrc;
    @Inject
    private SeatDao seatDao;
    @Inject
    private SeatTypeDao seatTypeDao;

    public void createSeatType(SeatType seatType) throws Exception {
        log.info("Registering " + seatType.getDescription());
        seatTypeDao.persist(seatType);
        seatTypeEventSrc.fire(seatType);
    }

    public void createTheatre(List<SeatType> seatTypes) {
        for (SeatType type : seatTypes) {
            for (int ii = 0; ii < type.getQuantity(); ii++) {
                final Seat seat = new Seat();
                seat.setBooked(false);
                seat.setSeatType(type);
                seatDao.persist(seat);
            }
        }
    }

    public void bookSeat(long seatId) {
        final Seat seat = seatDao.find(seatId);
        seat.setBooked(true);
        seatDao.persist(seat);
        seatEventSrc.fire(seat);
    }

    public void doCleanUp() {
        seatDao.deleteAll();
        seatTypeDao.deleteAll();
    }
}
