package com.company.models.players.mcts;

import com.company.models.Board;
import com.company.models.exceptions.InValidMove;
import com.company.models.move.Move;

import java.time.LocalTime;
import java.util.ArrayList;


public class MCTS {
    LocalTime stTime;
    final int MAX_SECONDS = 5;
    

    public void getBestMove(MCTSNode node){
        this.stTime = LocalTime.now();

        while(stTime.plusSeconds(MAX_SECONDS).compareTo(LocalTime.now())==1){ //While time isn't up look for a good move
            MCTSNode promisingNode = this.selectBestNode(node);

            if(promisingNode.getChildren().size()==0){
                this.expandNode(node);
            }
        }

    }

    private void expandNode(MCTSNode parent) {
        ArrayList<Move> possibleMoves = parent.getState().getBoard().reachablePositionsByPlayer();

        for (Move mv :
                possibleMoves) {
            Board board = new Board(parent.getState().getBoard());
            try {
                board.makeMove(mv);
            } catch (InValidMove e) {
                throw new RuntimeException(e);
            }

            NodeState childState = new NodeState(board);
            MCTSNode child = new MCTSNode(childState,parent);
            parent.addChild(child);
        }

    }

    private MCTSNode selectBestNode(MCTSNode node){
        MCTSNode cpNode = node;

        while(cpNode.getChildren().size()!=0){
            cpNode = UTC.findBestNodeWithUCT(cpNode);
        }
        return  cpNode;

    }
}
