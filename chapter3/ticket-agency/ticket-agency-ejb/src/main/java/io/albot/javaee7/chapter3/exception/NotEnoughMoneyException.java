package io.albot.javaee7.chapter3.exception;

/**
 * @author D. Albot
 * @date 03.10.2016
 */
public class NotEnoughMoneyException extends Exception {
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
