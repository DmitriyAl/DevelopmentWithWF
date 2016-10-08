package io.albot.javaee7.chapter5.control;

import io.albot.javaee7.chapter5.entity.SeatType;

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
