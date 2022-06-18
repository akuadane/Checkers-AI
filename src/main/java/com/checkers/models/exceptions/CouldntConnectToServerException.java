package com.checkers.models.exceptions;

/**
 * Class CouldntConnectionToServerException represents an Exception in connecting to remote host
 */
public class CouldntConnectToServerException extends Exception {
    public CouldntConnectToServerException(String message) {
        super(message);
    }
}
