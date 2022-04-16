package com.company.test.models;
import com.company.main.models.Board;
import com.company.main.models.move.Jump;
import com.company.main.models.move.Move;
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

    @Test
    public void makeMoveTest(){}

    @Test
    public void undoTest(){}

    @Test
    public void isGameOverTest(){}

}
