package com.checkersai.models.players;

import com.checkersai.models.Board;

public interface AIPlayer {
    public double evalBoard(Board board);
}
