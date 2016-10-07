package io.albot.javaee7.chapter3.client;

import io.albot.javaee7.chapter3.boundary.TheatreBookerRemote;
import io.albot.javaee7.chapter3.boundary.TheatreInfoRemote;
import io.albot.javaee7.chapter3.exception.NoSuchSeatException;
import io.albot.javaee7.chapter3.exception.NotEnoughMoneyException;
import io.albot.javaee7.chapter3.exception.SeatBookedException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author D. Albot
 * @date 05.10.2016
 */
public class TicketAgencyClient {
    private static final Logger log = Logger.getLogger(TicketAgencyClient.class.getName());
    private final List<Future<String>> lastBookings = new ArrayList<>();
    private final Context context;
    private TheatreInfoRemote theatreInfo;
    private TheatreBookerRemote theatreBooker;

    public TicketAgencyClient() throws NamingException {
        final Properties jndiProperties = new Properties();
        jndiProperties.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        this.context = new InitialContext(jndiProperties);
    }

    private enum Command {
        BOOK, LIST, MONEY, BOOKASYNC, MAIL, QUIT, INVALID;

        public static Command parseCommand(String stringCommand) {
            try {
                return valueOf(stringCommand.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                return INVALID;
            }
        }
    }


    public static void main(String[] args) throws NamingException {
        Logger.getLogger("org.jboss").setLevel(Level.SEVERE);
        Logger.getLogger("org.xnio").setLevel(Level.SEVERE);

        new TicketAgencyClient().run();
    }

    private void run() throws NamingException {
        this.theatreInfo = lookupTheatreInfoEJB();
        this.theatreBooker = lookupTheatreBookerEJB();

        showWelcomeMessage();

        while (true) {
            final String stringCommand = IOUtils.readLine("> ");
            final Command command = Command.parseCommand(stringCommand);
            switch (command) {
                case BOOK:
                    handleBook();
                    break;
                case LIST:
                    handleList();
                    break;
                case MONEY:
                    handleMoney();
                    break;
                case BOOKASYNC:
                    handleBookAsync();
                    break;
                case MAIL:
                    handleMail();
                    break;
                case QUIT:
                    handleQuit();
                    break;
                default:
                    log.warning("Unknown command " + stringCommand);
            }
        }
    }

    private void handleBook() {
        int seatId;
        try {
            seatId = IOUtils.readInt("Enter SeatId: ");
        } catch (NumberFormatException e) {
            log.warning("Wrong seatId format!");
            return;
        }
        try {
            final String retVal = theatreBooker.bookSeat(seatId);
            System.out.println(retVal);
        } catch (SeatBookedException | NotEnoughMoneyException | NoSuchSeatException e) {
            log.warning(e.getMessage());
        }
    }

    private void handleList() {
        log.info(theatreInfo.printSeatList());
    }

    private void handleMoney() {
        final int accountBalance = theatreBooker.getAccountBalance();
        log.info(("you have: " + accountBalance + " money left."));
    }

    private void handleBookAsync() {
        String text = IOUtils.readLine("Enter SeatId: ");
        int seatId;

        try {
            seatId = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            log.warning("Wrong seatId format!");
            return;
        }

        lastBookings.add(theatreBooker.bookSeatAsyncFuture(seatId));
        log.info("Booking issued. Verify your mail!");
    }

    private void handleMail() {
        boolean displayed = false;
        final List<Future<String>> notFinished = new ArrayList<>();
        for (Future<String> booking : lastBookings) {
            if (booking.isDone()) {
                try {
                    final String result = booking.get();
                    log.info("Mail received: " + result);
                    displayed = true;
                } catch (InterruptedException | ExecutionException e) {
                    log.warning(e.getMessage());
                }
            } else {
                notFinished.add(booking);
            }
        }

        lastBookings.retainAll(notFinished);
        if (!displayed) {
            log.info("No mail received!");
        }
    }

    private void handleQuit() {
        log.info("Bye");
        System.exit(0);
    }

    private TheatreInfoRemote lookupTheatreInfoEJB() throws NamingException {
        return (TheatreInfoRemote) context.lookup("ejb:/ticket-agency-ejb//TheatreInfo!TheatreInfoRemote");
    }

    private TheatreBookerRemote lookupTheatreBookerEJB() throws NamingException {
        return (TheatreBookerRemote) context.lookup("ejb:/ticket-agency-ejb//TheatreBooker!TheatreBookerRemote?stateful");
    }

    private void showWelcomeMessage() {
        System.out.println("Theatre booking system");
        System.out.println("===================================");
        System.out.println("Commands: book, bookAsync, mail, list, money, quit");
    }
}
