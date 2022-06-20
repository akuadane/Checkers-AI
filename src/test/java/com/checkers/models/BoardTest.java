package com.checkers.models;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.move.Jump;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;
import org.junit.Test;
import org.junit.Before;

import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    Board board= new Board();

    @Before
    public void resetBoard(){
        board = new Board();
    }

    @Test
    public void findMoveForPlayerOneTest(){
        List<Move> playerOneMoves = board.reachablePositionsByPlayer(Piece.PieceOwner.PLAYER1);

        assertEquals(7,playerOneMoves.size()); // at an initial state each player has 7 moves to choose from
        for (Move mv :
                playerOneMoves) {

            assertEquals(mv.getOrigin().getRow(),5); // for player1 the initial movements should start from row 5
            assertFalse(mv instanceof Jump);  // the first moves can't be a Jump
        }

    }
    @Test
    public void findMoveForPlayerTwoTest(){

        List<Move> playerTwoMoves = board.reachablePositionsByPlayer(Piece.PieceOwner.PLAYER2);
        assertEquals(7,playerTwoMoves.size()); // at an initial state each player has 7 moves to choose from
        for (Move mv :
                playerTwoMoves) {

            assertEquals(mv.getOrigin().getRow(),2); // for player1 the initial movements should start from row 5
            assertFalse(mv instanceof Jump);  // the first moves can't be a Jump
        }

    }

    @Test
    public void findJumpTest(){}

    @Test(expected = InValidMove.class)
    public void makeIllegalMoveTest() throws InValidMove {
        board.makeMove(null);
    }

    @Test
    public void makeMoveTest(){
        Move mv = board.reachablePositionsByPlayer(Piece.PieceOwner.PLAYER1).get(0);
        Piece p = board.getPiece(mv.getOrigin());
        try {
            board.makeMove(mv);
            assertEquals(board.getPiece(mv.getOrigin()),null);
            assertEquals(board.getPiece(mv.getDestination()),p);
        } catch (InValidMove e) {
            System.out.println("Failure");

        }
    }

    @Test
    public void undoTest() throws InValidMove {
        Board prevBoard = new Board(board);
        Move mv = board.reachablePositionsByPlayer(Piece.PieceOwner.PLAYER1).get(0);

        board.makeMove(mv);
        assertNotEquals(prevBoard,board);

        board.undo();
        assertEquals(prevBoard,board);

    }
    @Test
    public void redoTest() throws InValidMove {
        Board prevBoard = new Board(board);
        Move mv = board.reachablePositionsByPlayer(Piece.PieceOwner.PLAYER1).get(0);

        board.makeMove(mv);
        assertNotEquals(prevBoard,board);

        Board afterMoveBoard = new Board(board);

        board.undo();
        assertEquals(prevBoard,board);

        board.redo();
        assertEquals(board,afterMoveBoard);

    }

    @Test
    public void isGameOverTest(){
        assertNull(board.isGameOver());
    }

}
