package com.company.models.players;

import com.company.ReinforcementLearning.QTable;
import com.company.models.Board;
import com.company.models.exceptions.InValidMove;
import com.company.models.move.Move;
import com.company.models.piece.Piece;

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
