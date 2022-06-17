package com.checkers.models.players.mcts;

import com.checkers.controller.Game;
import com.checkers.models.Board;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;
import com.checkers.models.players.Player;
import com.checkers.models.players.RandomPlayer;

import java.time.LocalTime;
import java.util.ArrayList;


/**
 * Builds the tree and performs all the necessary actions needed to implement Monte Carlo Tree Search.
 * It selects, expands, Simulate and Updates the tree.
 * */
public class MCTS {
    LocalTime stTime;
    final int MAX_SECONDS = 5;

    private int numberOfSimulations = 10;

    public MCTS(){}
    public MCTS(int numberOfSimulations){
        this.numberOfSimulations = numberOfSimulations;
    }

    public Move getBestMove(MCTSNode node) throws InValidMove, CloneNotSupportedException {
        this.stTime = LocalTime.now();
        int noPlays =0;
        while(stTime.plusSeconds(MAX_SECONDS).compareTo(LocalTime.now())==1){ //While time isn't up look for a good move
            MCTSNode promisingNode = this.selectBestNode(node);
            this.expandNode(promisingNode);

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
    /**
     * Selects the best out of the possible moves for the root node.
     *
     * @param root The root of the tree.
     * @param noPlays The total number games simulated.
     * @return The index of the best move.
     * */
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

    /**
     * Creates nodes based on the possible moves from that state. The newly created nodes
     * will have the same parent and are different from each other by the move made to reach their state from
     * their parent.
     *
     * @param parent The parent node from which they are created.
     * */
    private void expandNode(MCTSNode parent) throws InValidMove {
        ArrayList<Move> possibleMoves = parent.getState().getBoard().reachablePositionsByPlayer();

        for (Move mv :
                possibleMoves) {
            Board board = new Board(parent.getState().getBoard());
            board.makeMove(mv);
            NodeState childState = new NodeState(board);
            MCTSNode child = new MCTSNode(childState,parent);
            parent.addChild(child);
        }

    }
    /**
     * Simulates the game to know what the end state of the node will be.
     *
     * @param node The node from which we will start our simulation.
     * @return The winner of the simulation.
     * */
    private Piece.PieceOwner simulateGame(MCTSNode node) throws InValidMove, CloneNotSupportedException {
        Game simulation = new Game(new RandomPlayer("P1", Piece.PieceOwner.PLAYER1),
                                    new RandomPlayer("P2", Piece.PieceOwner.PLAYER2),
                                   new Board(node.getState().getBoard()));
        Player winner = simulation.playWithoutDebugging();
        return winner.myTurn;
    }


    /**
     * Updates all of a node's parents with the result obtained from a simulation.
     *
     * @param cpPromisingNode The node and its parents are going to be updated.
     * @param winner The result from the simulation.
     * */
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
