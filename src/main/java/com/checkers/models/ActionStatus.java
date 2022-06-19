package com.checkers.models;

/**
 * Enum ActionStatus represents the statuses of connection establishment process in remote game
 */
public enum ActionStatus {
    NOT_STARTED, JOINING_GAME, HOSTING_GAME, WAITING_FOR_OPPONENT, COMPLETED, FAILED
}
