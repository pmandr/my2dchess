package xadrez.model;

import java.awt.Point;
import java.util.ArrayList;
import xadrez.controller.IlegalChessMovement;
import xadrez.controller.Board;

public class Tower extends Piece {
    
    
    public Tower(){
        super();
    }
    public Tower(Piece peca){
        super(peca);
    }
    public Tower(Point position, boolean is_white) throws IlegalChessMovement {
        super(position, is_white);
        this.setFileName("torre_" + (is_white ? "branco" : "preto") + ".png");
        setPieceType("torre");
    }
    public Tower(PieceJSON piece_json) {
        super(piece_json);
    }

    

    @Override
    public void calculatePossibleMoves() {
        this.possible_movements = new ArrayList<Point>();
        Point p_temp;

        //Movement of the tower to the right - movimento da torre para a direita
        p_temp = new Point((int) this.getPosition().getX() + 1, (int) this.getPosition().getY());
        if (Piece.isOnTheBoard(p_temp)) {
            while (Board.getPiece(p_temp) == null && Piece.isOnTheBoard(p_temp)) {
                this.possible_movements.add(new Point(p_temp));
                p_temp.setLocation(p_temp.getX() + 1, p_temp.getY());
            }
            if (Piece.isOnTheBoard(p_temp)
                    && Board.getPiece(p_temp) != null
//                    && Board.getPiece(p_temp).isWhite() != Board.isWhiteTurn()) { //teste para saber se a peca eh amiga ou inimiga
                    && Board.getPiece(p_temp).isWhite() != this.isWhite()) { //teste para saber se a peca eh amiga ou inimiga
                possible_movements.add(new Point(p_temp));
            }
        }
        
        //Movement of the tower to the left - movimento da torre para a esquerda
        p_temp = new Point((int) this.getPosition().getX() - 1, (int) this.getPosition().getY());
        if (Piece.isOnTheBoard(p_temp)) {
            while (Board.getPiece(p_temp) == null && Piece.isOnTheBoard(p_temp)) {
                this.possible_movements.add(new Point(p_temp));
                p_temp.setLocation(p_temp.getX() - 1, p_temp.getY());
            }
            if (Piece.isOnTheBoard(p_temp)
                    && Board.getPiece(p_temp) != null
                    && Board.getPiece(p_temp).isWhite() != this.isWhite()) { //teste para saber se a peca eh amiga ou inimiga
                possible_movements.add(new Point(p_temp));
            }
        }
        
        //Movement of the rainha to upward direction - movimento da torre para a cima
        p_temp = new Point((int) this.getPosition().getX() , (int) this.getPosition().getY() - 1);
        if (Piece.isOnTheBoard(p_temp)) {
            while (Board.getPiece(p_temp) == null && Piece.isOnTheBoard(p_temp)) {
                this.possible_movements.add(new Point(p_temp));
                p_temp.setLocation(p_temp.getX() , p_temp.getY() -1);
            }
            if (Piece.isOnTheBoard(p_temp)
                    && Board.getPiece(p_temp) != null
                    && Board.getPiece(p_temp).isWhite() != this.isWhite()) { //teste para saber se a peca eh amiga ou inimiga
                 possible_movements.add(new Point(p_temp));
            }
        }
        //Movement of the tower to downward direction - movimento da torre para a baixo
        p_temp = new Point((int) this.getPosition().getX() , (int) this.getPosition().getY() +1);
        if (Piece.isOnTheBoard(p_temp)) {
            while (Board.getPiece(p_temp) == null && Piece.isOnTheBoard(p_temp)) {
                this.possible_movements.add(new Point(p_temp));
                p_temp.setLocation(p_temp.getX() , p_temp.getY()+1);
            }
            if (Piece.isOnTheBoard(p_temp)
                    && Board.getPiece(p_temp) != null
                    && Board.getPiece(p_temp).isWhite() != this.isWhite()) { //teste para saber se a peca eh amiga ou inimiga
                possible_movements.add(new Point(p_temp));
            }
        }
    }
}
