package io.albot.javaee7.chapter6.util;

import io.albot.javaee7.chapter6.jms.BookingCompletionListener;

import javax.enterprise.inject.Produces;
import javax.jms.CompletionListener;

/**
 * @author D. Albot
 * @date 09.10.2016
 */
public class BookingCompletionListenerProducer {
//    @Produces
//    @BookingComplInject
    public CompletionListener produce() {
        return new BookingCompletionListener();
    }
}
