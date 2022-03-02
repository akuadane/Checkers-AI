package com.company;

public class Board {
    Piece[][] board = new Piece[8][8];
    int jumpList[][] = new int[48][12];
    int moveList[][] = new int[48][12];

    public Board(){
        this.resetBoard();
    }

    public int[][] findLegalMoves(PieceOwner owner){
        return new int[48][12];
    }
    private void findJumps(PieceOwner owner,int x, int y){}
    private void findKingJumps(PieceOwner owner,int x, int y){}
    private void findMoves(PieceOwner owner,int x, int y){}
    private void findKingMoves(PieceOwner owner,int x, int y){}
    public void makeMove(int[] move){}
    public void makeJump(int[] jump){}

    /**
     * Resets the board to its original state.
     * */
    public void resetBoard(){

        for(int i=0;i<=7;i++){
            for(int j=(1-i%2);j<=7;j+=2){ // Makes sure the Pieces are placed on squares where i+j is odd
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

    public void display(){
        for(int i=0;i<=7;i++){
            for(int j=0;j<=7;j++){
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
