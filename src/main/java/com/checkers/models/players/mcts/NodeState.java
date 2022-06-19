package com.checkers.models.players.mcts;

import com.checkers.models.Board;
import com.checkers.models.piece.Piece;

public class NodeState {
    private Board board;

    private int wins;
    private int plays;

    public NodeState(Board board){
        this.board = board;
        this.wins = 0;
        this.plays = 0;
    }
    public NodeState(NodeState nodeState){
        this.board = new Board(nodeState.board);
        this.wins = nodeState.wins;
        this.plays = nodeState.plays;

    }

    public Board getBoard() {
        return board;
    }


    public int getWins() {
        return wins;
    }

    public int getPlays() {
        return plays;
    }

    public void incrementPlays() {
    this.plays++;
    }

    public void incrementWins() {this.wins++;}
}
