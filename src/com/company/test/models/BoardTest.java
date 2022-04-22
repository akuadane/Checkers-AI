package com.company.test.models;
import com.company.main.models.Board;
import com.company.main.models.exceptions.InValidMove;
import com.company.main.models.move.Jump;
import com.company.main.models.move.Move;
import com.company.main.models.piece.Piece;
import com.company.main.models.piece.PieceOwner;
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
        List<Move> playerOneMoves = board.findLegalMoves(PieceOwner.PLAYER1);

        assertEquals(7,playerOneMoves.size()); // at an initial state each player has 7 moves to choose from
        for (Move mv :
                playerOneMoves) {

            assertEquals(mv.getOrigin().getRow(),5); // for player1 the initial movements should start from row 5
            assertFalse(mv instanceof Jump);  // the first moves can't be a Jump
        }

    }
    @Test
    public void findMoveForPlayerTwoTest(){

        List<Move> playerTwoMoves = board.findLegalMoves(PieceOwner.PLAYER2);
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
    public void makeIllegalMoveTest() throws InValidMove, CloneNotSupportedException {
    board.makeMove(null,PieceOwner.PLAYER1);
    }

    @Test
    public void makeMoveTest(){
        Move mv = board.findLegalMoves(PieceOwner.PLAYER1).get(0);
        Piece p = board.getPiece(mv.getOrigin());
        try {
            board.makeMove(mv,PieceOwner.PLAYER1);
            assertEquals(board.getPiece(mv.getOrigin()),null);
            assertEquals(board.getPiece(mv.getDestination()),p);
        } catch (InValidMove e) {
            System.out.println("Failure");

        }
    }

    @Test
    public void undoTest() throws InValidMove, CloneNotSupportedException {
        Board prevBoard = board.clone();
        Move mv = board.findLegalMoves(PieceOwner.PLAYER1).get(0);

        board.makeMove(mv,PieceOwner.PLAYER1);
        assertNotEquals(prevBoard,board);

        board.undo();
        assertEquals(prevBoard,board);

    }

    @Test
    public void isGameOverTest(){
        assertEquals(board.isGameOver(),null);
    }

}
