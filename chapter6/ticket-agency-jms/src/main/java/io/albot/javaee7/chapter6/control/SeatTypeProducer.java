package io.albot.javaee7.chapter6.control;

import io.albot.javaee7.chapter6.entity.SeatType;
import io.albot.javaee7.chapter6.util.SeatTypeInjection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author D. Albot
 * @date 08.10.2016
 */
@RequestScoped
public class SeatTypeProducer {
    @Inject
    private SeatTypeDao seatTypeDao;
    private List<SeatType> seatTypes;

    @PostConstruct
    public void retrieveAllSeatTypes() {
        seatTypes = seatTypeDao.findAll();
    }

    @Produces
    @Named
    @SeatTypeInjection
    public List<SeatType> getSeatTypes() {
        return seatTypes;
    }

    public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final SeatType member) {
        retrieveAllSeatTypes();
    }
}
