package io.albot.javaee7.chapter5.controller;

import io.albot.javaee7.chapter5.control.TicketService;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author D. Albot
 * @date 08.10.2016
 */
@Named
@ViewScoped
public class BookerService implements Serializable {
    @Inject
    private Logger log;
    @Inject
    private TicketService ticketService;
    @Inject
    private FacesContext context;
    private int money;

    @PostConstruct
    public void createCustomer() {
        this.money = 100;
    }

    public void bookSeat(long seatId, int price) {
        log.info("Booking seat: " + seatId);
        if (price > money) {
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR
                    , "Not enough Money!", "Registration failed");
            context.addMessage(null, m);
            return;
        }
        ticketService.bookSeat(seatId);
        final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO
                , "Registered!", "Registration successful");
        context.addMessage(null, m);
        log.info("Seat booked.");
        money -= price;
    }

    public int getMoney() {
        return money;
    }
}
