package com.company;

import com.company.move.Move;
import com.company.move.Jump;

import java.util.*;

public class Board {
    private Piece[][] board = new Piece[8][8];
    private Piece[][] prevBoard;
    List<Move> moveList = new ArrayList<>();
    List<Jump> jumpList = new ArrayList<>();

    public Board(){
        this.resetBoard();
    }

    /**
     * Locate Pieces owned by inTurnPlayer and find the possible moves
     * It populates jumpList and moveList, if jumpList is empty
     * */
    public void findLegalMoves(PieceOwner inTurnPlayer){

        for(int r=0;r<this.board.length;r++){
            for(int c=(1-r%2);c<this.board.length;c+=2){ // Makes sure the Pieces are placed on squares where i+j is odd
                Piece piece = this.board[r][c];
                if(piece!=null && piece.owner==inTurnPlayer){
                        jumpList.addAll(findJumps(piece,r,c)); // add all possible jumps

                        if(jumpList.size()==0) // If there is no jump, then look for normal moves
                            moveList.addAll(findMoves(piece,r,c));
                }
            }
        }
    }

    /**
     * Find all possible jumps from the square [r,c]
     * */
    private List<Jump> findJumps(Piece piece, int r, int c){

        List<Jump> jumpList = new ArrayList<>();

        for(Move mv: findMoves(piece,r,c)){  // Use BreadthFirst search to locate all possible jumps from this point
            if(!(mv instanceof Jump))
                continue;
            Queue<Jump> queue = new LinkedList<>(); // instatia
            queue.add((Jump) mv);  //
            
            while(queue.size()>0){

                Jump x =queue.remove();
                boolean addToJumpList = true;

                for(Move nMove: findMoves(piece,x.movement[2],x.movement[3])){
                    if(!(nMove instanceof Jump))
                        continue;
                    
                    Jump rm = new Jump(new int[]{x.movement[0],x.movement[1],nMove.movement[2],nMove.movement[3]});
                    rm.toBeRemoved.addAll(x.toBeRemoved);
                    rm.toBeRemoved.addAll(((Jump) nMove).toBeRemoved);
                    queue.add(rm);
                    addToJumpList=false;
                }

                if(addToJumpList) // if the move doesn't have any more jumps add it to jumpList
                    jumpList.add(x);
            }

        }


        return jumpList;
    }
    /**
     * Finds all possible moves and jumps from the square [r,c] in one-step range
     * */
    private List<Move> findMoves(Piece piece,int r, int c){
        int[][] moveDir;
        List<Move> tempMoveList = new ArrayList<>();

        if(piece.type==PieceType.PAWN){
            if(piece.owner==PieceOwner.PLAYER1 )
                moveDir= new int[][]{{-1,-1},{-1,1}}; // allows player1's pawns to move towards the top of the board only
            else
                moveDir= new int[][]{{1,-1},{1,1}};  // allows player2's pawns to move towards the bottom of the board only
        }
        else
            moveDir= new int[][]{{1,-1},{1,1},{-1,1},{-1,-1}}; // allows kings to move to every direction

        for(int i=0;i< moveDir.length;i++){
            int newR = r+moveDir[i][0];
            int newC = c+moveDir[i][1];

            //Check if the new row and column are inside the board
            if(newR<0 || newR>=this.board.length || newC<0 || newC>=this.board.length)
                continue;


            Piece newPos = this.board[newR][newC];

            // check if the adjacent square is empty
            if(newPos==null) {
                Move mv = new Move(new int[]{r, c, newR, newC});
                tempMoveList.add(mv);

            }
            // check if the adjacent square is occupied by our piece, if so continue
            else if(newPos.owner==piece.owner)
                continue;
            // check if the second adjacent square is occupied or not
            else if(piece.type==PieceType.KING || newPos.type==PieceType.PAWN) { // Makes sure a PAWN doesn't take a KING
                int nextNewR = newR + moveDir[i][0];
                int nextNewC = newC + moveDir[i][1];

                if(nextNewR<0 || nextNewR>=this.board.length || nextNewC<0 || nextNewC>=this.board.length)
                    continue;
                Piece nextNewPos = this.board[nextNewR][nextNewC];

                // if the second adjacent square isn't occupied  this is a valid removing move
                if(nextNewPos==null){
                    Jump rm = new Jump(new int[]{r, c, nextNewR, nextNewC});
                    rm.addToBeRemovedSquare(new int[]{newR,newC});

                    tempMoveList.add(rm);
                }


        }
    }
        return tempMoveList;
    }

    /**
     * Moves a piece on the board
     * */
    public void makeMove(Move move){

        prevBoard = Arrays.copyOf(board,board.length);

        int initR = move.movement[0];
        int initC = move.movement[1];
        int newR = move.movement[2];
        int newC = move.movement[3];

        // Put the piece onto its new destination
        board[newR][newC] = board[initR][initC].copy();
        board[initR][initC] = null;  // Make the previous position empty

        if(move instanceof Jump){
            for(int[] remove: ((Jump) move).toBeRemoved){
                board[remove[0]][remove[1]]=null;  // Remove all piece that are jumped over
            }
        }
    }

    /**
     * Resets the board to its original state.
     * */
    public void resetBoard(){

        for(int i=0;i<this.board.length;i++){
            for(int j=(1-i%2);j<this.board.length;j+=2){ // Makes sure the Pieces are placed on squares where i+j is odd
                if(i<=2){  //place player two's pawns in their starting place
                    this.board[i][j]= new Piece(PieceType.PAWN,PieceOwner.PLAYER2);
                }else if(i<=4){ // makes the middle two rows empty
                    this.board[i][j]=null;
                }else{ //place player one's pawns in their starting place
                    this.board[i][j]= new Piece(PieceType.PAWN,PieceOwner.PLAYER1);
                }

            }
        }

    }

    /**
     * Restores the previous state of the board
     * prevBoard holds the board state just before the last move
     * */
    public void undo(){
        if(prevBoard!=null)
            board = Arrays.copyOf(prevBoard,prevBoard.length);
    }
    public void display(){
        for(int i=0;i<this.board.length;i++){
            for(int j=0;j<this.board.length;j++){
                Piece piece = this.board[i][j];
                char p = ' ';
                if(piece!=null){
                    if(piece.owner==PieceOwner.PLAYER1){
                        p='a';
                    }else{
                        p='b';
                    }
                    if(piece.type==PieceType.KING)
                        p = Character.toUpperCase(p);
                }
                System.out.print("|"+p);
            }
            System.out.println();
        }
    }

}
