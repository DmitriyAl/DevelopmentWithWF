package io.albot.javaee7.chapter4.controller;

import io.albot.javaee7.chapter4.boundary.TheatreBox;
import io.albot.javaee7.chapter4.util.Logged;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author D. Albot
 * @date 06.10.2016
 */
@Named
@ViewScoped
@Logged
public class TheatreBooker implements Serializable {
    @Inject
    private Logger log;
    @Inject
    private TheatreBox theatreBox;
    @Inject
    private FacesContext facesContext;
    private int money;

    @PostConstruct
    public void createCustomer() {
        this.money = 100;
    }

    public void bookSeat(int seatId) {
        log.info("Booking seat " + seatId);
        int seatPrice = theatreBox.getSeatPrice(seatId);

        if (seatPrice > money) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR
                    , "Not enough Money!", "Registration unsuccessful");
            facesContext.addMessage(null, m);
            return;
        }

        theatreBox.buyTicket(seatId);

        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO
                , "Booked!", "Booking successful");
        facesContext.addMessage(null, m);
        log.info("Seat booked.");
        money -= seatPrice;
    }

    public int getMoney() {
        return money;
    }
}
