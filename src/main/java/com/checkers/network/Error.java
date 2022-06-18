package com.checkers.network;

/**
 * Class Error signifies an error message send between remote players
 */
public class Error extends Action {
    public String message;

    public Error(String message) {
        this.message = message;
    }
}
