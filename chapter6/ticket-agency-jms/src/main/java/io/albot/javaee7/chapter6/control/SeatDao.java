package io.albot.javaee7.chapter6.control;

import io.albot.javaee7.chapter6.entity.Seat;

import javax.ejb.Stateless;

/**
 * @author D. Albot
 * @date 08.10.2016
 */
@Stateless
public class SeatDao extends AbstractDao<Seat> {
    public SeatDao() {
        super(Seat.class);
    }
}
