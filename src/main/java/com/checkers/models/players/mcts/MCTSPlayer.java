package com.checkers.models.players.mcts;

import com.checkers.models.Board;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;
import com.checkers.models.players.Player;

public class MCTSPlayer extends Player {

    MCTS mcts;

    public MCTSPlayer(){
        super("MCTS_Player");
        mcts = new MCTS();
    }
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
