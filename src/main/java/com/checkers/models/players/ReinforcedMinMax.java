package com.checkers.models.players;

import com.checkers.models.Board;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;

public class ReinforcedMinMax extends Player {
    private AlphaBetaMinMaxAIPlayer minMaxAIPlayer;
    private ReinforcedPlayer reinforcedPlayer;

    public ReinforcedMinMax(String name, String version, Piece.PieceOwner myTurn) {
        super(name, myTurn);
        this.minMaxAIPlayer = new AlphaBetaMinMaxAIPlayer(name, myTurn);
        this.reinforcedPlayer = new ReinforcedPlayer(name, version, myTurn);
    }

    public ReinforcedMinMax() {
        super("ReinforcedMinMaxPlayer");
        this.minMaxAIPlayer = new AlphaBetaMinMaxAIPlayer(name, myTurn);
        this.reinforcedPlayer = new ReinforcedPlayer(name,"Four-Player",myTurn);
    }
    public ReinforcedMinMax(String name, Piece.PieceOwner myTurn){
        super(name, myTurn);
        this.minMaxAIPlayer = new AlphaBetaMinMaxAIPlayer(name, myTurn);
        this.reinforcedPlayer = new ReinforcedPlayer(name, "Four-Player", myTurn);
    }

    @Override
    public Move makeMove(Board board) throws InValidMove, CloneNotSupportedException {
        int totalPieceCount = 0;
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = (1 - i % 2); j < Board.BOARD_SIZE; j += 2) {
                if (!board.isEmpty(i, j))
                    totalPieceCount++;
            }
        }
        if (totalPieceCount > 4)
            return this.minMaxAIPlayer.makeMove(board);
        return this.reinforcedPlayer.makeMove(board);

    }
}
