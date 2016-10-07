package io.albot.javaee7.chapter3.exception;

/**
 * @author D. Albot
 * @date 30.09.2016
 */
public class NoSuchSeatException extends Exception {
    public NoSuchSeatException(String message) {
        super(message);
    }
}
