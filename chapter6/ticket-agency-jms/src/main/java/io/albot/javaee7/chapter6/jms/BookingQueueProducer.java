package io.albot.javaee7.chapter6.jms;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * @author D. Albot
 * @date 09.10.2016
 */
@ApplicationScoped
public class BookingQueueProducer {
    @Inject
    private JMSContext context;
    @Inject
//    @BookingComplInject
    private BookingCompletionListener completionListener;
    @Resource(mappedName = BookingQueueDefinition.BOOKING_QUEUE)
    private Queue syncQueue;

    public void sendMessage(String txt, Priority priority) {
        context.createProducer().setAsync(completionListener).setProperty("priority", priority.toString()).send(syncQueue, txt);
    }
}
