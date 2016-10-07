package io.albot.javaee7.chapter3.boundary;

import io.albot.javaee7.chapter3.control.TheatreBox;
import io.albot.javaee7.chapter3.entity.Seat;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.Collection;

/**
 * @author D. Albot
 * @date 03.10.2016
 */
@Stateless
@Remote(TheatreInfoRemote.class)
public class TheatreInfo implements TheatreInfoRemote {
    @EJB
    private TheatreBox box;

    @Override
    public String printSeatList() {
        final Collection<Seat> seats = box.getSeats();
        final StringBuilder sb = new StringBuilder();
        for (Seat seat : seats) {
            sb.append(seat.toString());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
