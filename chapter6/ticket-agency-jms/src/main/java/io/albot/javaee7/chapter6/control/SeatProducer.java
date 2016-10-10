package io.albot.javaee7.chapter6.control;

import io.albot.javaee7.chapter6.entity.Seat;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author D. Albot
 * @date 08.10.2016
 */
@RequestScoped
public class SeatProducer implements Serializable {
    @Inject
    private SeatDao seatDao;
    private List<Seat> seats;

    @PostConstruct
    public void retrieveAllSeats() {
        seats = seatDao.findAll();
    }

    @Produces
    @Named
    public List<Seat> getSeats() {
        return seats;
    }

    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Seat member) {
        retrieveAllSeats();
    }
}
