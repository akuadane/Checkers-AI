package com.company.models.players.mcts;

import com.company.models.Board;
import com.company.models.exceptions.InValidMove;
import com.company.models.move.Move;
import com.company.models.piece.Piece;
import com.company.models.players.Player;

public class MCTSPlayer extends Player {

    MCTS mcts;

    public MCTSPlayer(String name, Piece.PieceOwner turn){
        super(name,turn);
        mcts = new MCTS();
    }
    @Override
    public Move makeMove(Board board) throws InValidMove, CloneNotSupportedException {
        NodeState state = new NodeState(new Board(board));
        MCTSNode root = new MCTSNode(state);
        return this.mcts.getBestMove(root);
    }
}
