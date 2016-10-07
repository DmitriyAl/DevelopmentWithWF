package io.albot.javaee7.chapter3.boundary;

import io.albot.javaee7.chapter3.control.TheatreBox;
import io.albot.javaee7.chapter3.exception.NoSuchSeatException;
import io.albot.javaee7.chapter3.exception.NotEnoughMoneyException;
import io.albot.javaee7.chapter3.exception.SeatBookedException;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author D. Albot
 * @date 03.10.2016
 */
@Stateful
@Remote(TheatreBookerRemote.class)
@AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
public class TheatreBooker implements TheatreBookerRemote {
    private static final Logger log = Logger.getLogger(TheatreBooker.class);
    private int money;
    @EJB
    private TheatreBox theatreBox;

    @PostConstruct
    public void createCustomer() {
        this.money = 100;
    }

    @Override
    public int getAccountBalance() {
        return money;
    }

    @Override
    public String bookSeat(int seatId) throws SeatBookedException, NoSuchSeatException, NotEnoughMoneyException {
        final int seatPrice = theatreBox.getSeatPrice(seatId);
        if (seatPrice > money) {
            throw new NotEnoughMoneyException("You don't have enough money to buy this "
                    + seatId + " seat!");
        }
        theatreBox.buyTicket(seatId);
        money = money - seatPrice;
        log.infov("Seat {0} booked.", seatId);
        return "Seat booked.";
    }

    @Asynchronous
    @Override
    public void bookSeatAsyncVoid(int seatId) throws SeatBookedException, NoSuchSeatException, NotEnoughMoneyException {
        bookSeat(seatId);
    }

    @Asynchronous
    @Override
    public Future<String> bookSeatAsyncFuture(int seatId) {
        try {
            Thread.sleep(10000);
            bookSeat(seatId);
            return new AsyncResult<>("Booked seat: " + seatId + ". Money left: " + money);
        } catch (InterruptedException | NoSuchSeatException | SeatBookedException | NotEnoughMoneyException e) {
            return new AsyncResult<>(e.getMessage());
        }
    }
}
