package com.company.test.models;
import com.company.main.models.Board;
import com.company.main.models.exceptions.InValidMove;
import com.company.main.models.move.Jump;
import com.company.main.models.move.Move;
import com.company.main.models.piece.Piece;
import com.company.main.models.piece.PieceOwner;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    Board board= new Board();

    @BeforeEach
    public void resetBoard(){
        board = new Board();
    }

    @Test
    public void findMoveForPlayerOneTest(){
        List<Move> playerOneMoves = board.findLegalMoves(PieceOwner.PLAYER1);

        assertEquals(7,playerOneMoves.size()); // at an initial state each player has 7 moves to choose from
        for (Move mv :
                playerOneMoves) {

            assertEquals(mv.movement[0],5); // for player1 the initial movements should start from row 5
            assertFalse(mv instanceof Jump);  // the first moves can't be a Jump
        }

    }
    @Test
    public void findMoveForPlayerTwoTest(){

        List<Move> playerTwoMoves = board.findLegalMoves(PieceOwner.PLAYER2);
        assertEquals(7,playerTwoMoves.size()); // at an initial state each player has 7 moves to choose from
        for (Move mv :
                playerTwoMoves) {

            assertEquals(mv.movement[0],5); // for player1 the initial movements should start from row 5
            assertFalse(mv instanceof Jump);  // the first moves can't be a Jump
        }

    }

    @Test
    public void findJumpTest(){}

    @Test(expected = InValidMove.class)
    public void makeIllegalMoveTest() throws InValidMove {
    board.makeMove(null,PieceOwner.PLAYER1);
    }

    @Test
    public void makeMoveTest(){
        Move mv = board.findLegalMoves(PieceOwner.PLAYER1).get(0);
        Piece p = board.getPiece(new int[]{mv.movement[0], mv.movement[1]});
        try {
            board.makeMove(mv,PieceOwner.PLAYER1);
            assertEquals(board.board[mv.movement[0]][mv.movement[1]],null);
            assertEquals(board.getPiece(new int[]{mv.movement[2], mv.movement[3]}),p);
        } catch (InValidMove e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void undoTest(){

    }

    @Test
    public void isGameOverTest(){}

}
