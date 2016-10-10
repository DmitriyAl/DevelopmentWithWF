package io.albot.javaee7.chapter6.control;

import io.albot.javaee7.chapter6.entity.SeatType;

import javax.ejb.Stateless;

/**
 * @author D. Albot
 * @date 08.10.2016
 */
@Stateless
public class SeatTypeDao extends AbstractDao<SeatType> {
    public SeatTypeDao() {
        super(SeatType.class);
    }
}
