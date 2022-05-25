package com.checkers.models.players;

import com.checkers.models.Board;

public interface AIPlayer {
    public double evalBoard(Board board);
}
