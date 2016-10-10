package io.albot.javaee7.chapter6.controller;

import io.albot.javaee7.chapter6.control.TicketService;
import io.albot.javaee7.chapter6.entity.SeatPosition;
import io.albot.javaee7.chapter6.entity.SeatType;
import io.albot.javaee7.chapter6.util.SeatTypeInjection;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

/**
 * @author D. Albot
 * @date 08.10.2016
 */
@Model
public class TheatreSetupService {
    @Inject
    private FacesContext facesContext;
    @Inject
    private TicketService ticketService;
    @Inject
    @SeatTypeInjection
    private List<SeatType> seatTypes;
    @Produces
    @Named
    private SeatType newSeatType;

    @PostConstruct
    public void initNewSeatType() {
        newSeatType = new SeatType();
    }

    public String createTheatre() {
        ticketService.createTheatre(seatTypes);
        return "book";
    }

    public String restart() {
        ticketService.doCleanUp();
        return "/index";
    }

    public void addNewSeats() {
        try {
            ticketService.createSeatType(newSeatType);
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO
                    , "Done!", "Seats added");
            facesContext.addMessage(null, m);
            initNewSeatType();
        } catch (Exception e) {
            final String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR
                    , errorMessage, "Error while saving data");
            facesContext.addMessage(null, m);
        }
    }

    private String getRootErrorMessage(Exception e) {
        String errorMessage = "Registration filed. See server log for more information";
        if (e == null) {
            return errorMessage;
        }
        Throwable t = e;
        while (t != null) {
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        return errorMessage;
    }

    public List<SeatPosition> getPositions() {
        return Arrays.asList(SeatPosition.values());
    }
}
