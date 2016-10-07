package io.albot.javaee7.chapter4.util;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

/**
 * @author D. Albot
 * @date 07.10.2016
 */
@Dependent
public class FacesContextProducer {
    @Produces
    @RequestScoped
    public FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
