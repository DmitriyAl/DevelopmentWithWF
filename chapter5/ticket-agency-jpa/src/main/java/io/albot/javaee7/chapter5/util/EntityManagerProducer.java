package io.albot.javaee7.chapter5.util;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author D. Albot
 * @date 08.10.2016
 */
public class EntityManagerProducer {
    @Produces
    @PersistenceContext
    private EntityManager em;
}
