package io.albot.javaee7.chapter3.boundary;

import io.albot.javaee7.chapter3.exception.NoSuchSeatException;
import io.albot.javaee7.chapter3.exception.NotEnoughMoneyException;
import io.albot.javaee7.chapter3.exception.SeatBookedException;

import java.util.concurrent.Future;

/**
 * @author D. Albot
 * @date 03.10.2016
 */
public interface TheatreBookerRemote {
    int getAccountBalance();

    String bookSeat(int seatId) throws SeatBookedException, NoSuchSeatException, NotEnoughMoneyException;
    void bookSeatAsyncVoid(int seatId) throws SeatBookedException, NoSuchSeatException, NotEnoughMoneyException;
    Future<String> bookSeatAsyncFuture(int seatId);
}
