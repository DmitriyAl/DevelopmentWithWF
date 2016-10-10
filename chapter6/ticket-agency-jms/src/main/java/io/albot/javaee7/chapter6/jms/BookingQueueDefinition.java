package io.albot.javaee7.chapter6.jms;

import javax.jms.JMSDestinationDefinition;

/**
 * @author D. Albot
 * @date 09.10.2016
 */
@JMSDestinationDefinition(name = BookingQueueDefinition.BOOKING_QUEUE
        , interfaceName = "javax.jms.Queue")
public class BookingQueueDefinition {
    public static final String BOOKING_QUEUE = "java:global/jms/bookingQueue";
}
