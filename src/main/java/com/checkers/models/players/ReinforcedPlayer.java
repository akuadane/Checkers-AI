package com.checkers.models.players;

import com.checkers.ReinforcementLearning.QTable;
import com.checkers.models.Board;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;

public class ReinforcedPlayer extends Player{
    QTable qTable;
    public ReinforcedPlayer(String name, String version, Piece.PieceOwner myTurn) {
        super(name, myTurn);
        this.qTable = new QTable(version);
    }

    public ReinforcedPlayer(String name) {
        super(name);
        this.qTable = new QTable();
    }
    public ReinforcedPlayer(){
        this("Reinforced Player");
    }

    @Override
    public Move makeMove(Board board) throws InValidMove, CloneNotSupportedException {
        int moveIndex = this.qTable.getAction(board);
        return board.reachablePositionsByPlayer(board.getTurn()).get(moveIndex);
    }
}
