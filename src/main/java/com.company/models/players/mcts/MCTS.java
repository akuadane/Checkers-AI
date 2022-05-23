package com.company.models.players.mcts;

import com.company.models.Board;
import com.company.models.exceptions.InValidMove;
import com.company.models.move.Move;
import com.company.models.piece.Piece;

import java.time.LocalTime;
import java.util.ArrayList;


public class MCTS {
    LocalTime stTime;
    final int MAX_SECONDS = 5;
    

    public Move getBestMove(MCTSNode node){
        this.stTime = LocalTime.now();
        int noPlays =0;
        while(stTime.plusSeconds(MAX_SECONDS).compareTo(LocalTime.now())==1){ //While time isn't up look for a good move
            MCTSNode promisingNode = this.selectBestNode(node);
            this.expandNode(node);

            MCTSNode cpPromisingNode = promisingNode;
            if(cpPromisingNode.getChildren().size()!=0)
                cpPromisingNode = cpPromisingNode.getRandomChild();

            Piece.PieceOwner winner = this.simulateGame(cpPromisingNode);
            this.backPropagate(cpPromisingNode,winner);

            noPlays++;

        }
        int bestMoveIndex = this.selectBestMove(node,noPlays);
        return node.getState().getBoard().reachablePositionsByPlayer().get(bestMoveIndex);

    }


    private MCTSNode selectBestNode(MCTSNode node){
        MCTSNode cpNode = node;
        while(cpNode.getChildren().size()!=0){
            cpNode = UTC.findBestNodeWithUCT(cpNode);
        }
        return  cpNode;
    }
    private int selectBestMove(MCTSNode root,int noPlays){


        int bestMvIndex=0;
        double winRate=0;
        int bestMvPlays =0;
        double bestScore =0;


        for (int i = 0; i < root.getChildren().size(); i++) {
            MCTSNode child = root.getChildren().get(i);

            int childWins = child.getState().getWins();
            int childPlays = child.getState().getPlays();

            if(childPlays==0)
                continue;


//            if(childWins/childPlays > winRate && bestMvPlays>childPlays)
//            {
//                winRate = childWins/childPlays;
//                bestMvPlays = childPlays;
//                bestMvIndex = i;
//
//            }

            if(childWins/childPlays + childPlays/noPlays > bestScore){
                bestScore = childWins/childPlays + childPlays/noPlays;
                bestMvIndex =i;
            }

        }

        return bestMvIndex;
        
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
    private Piece.PieceOwner simulateGame(MCTSNode node){
        return null; // TODO implement simulation
    }

    private void backPropagate(MCTSNode cpPromisingNode, Piece.PieceOwner winner) {
        MCTSNode temp = cpPromisingNode;

        while(temp!=null){
            temp.incrementPlays();
            if(temp.getState().getBoard().getTurn().equals(winner)){
                temp.incrementWins();
            }
            temp = temp.getParent();
        }
    }



}
