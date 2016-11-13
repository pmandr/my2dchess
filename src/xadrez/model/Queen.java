package xadrez.model;

import java.awt.Point;
import java.util.ArrayList;
import xadrez.controller.IlegalChessMovement;
import xadrez.controller.Board;

public class Queen extends Piece
{ 
    public Queen(PieceJSON piece_json) {
        super(piece_json);
    }
    public Queen(Piece piece){
        super(piece);
    }
    public Queen(Point position, boolean is_white) throws IlegalChessMovement {
        super(position, is_white);
        this.setFileName("rainha_"+(is_white?"branco":"preto")+".png");
        setPieceType("queen");
    }
    

    

    @Override
    public void calculatePossibleMoves() {
        this.possible_movements = new ArrayList<Point>();
        Point p_temp; //Ponto to go though the paths - Ponto que será usado para percorrer trajetos
        
        //1a diagonal (NORDESTE[Northeast]):
        p_temp = new Point((int) this.getPosition().getX()+1, (int) this.getPosition().getY()+1);
        if(Piece.isOnTheBoard(p_temp)){
            while(Board.getPiece(p_temp) == null && Piece.isOnTheBoard(p_temp)){
                this.possible_movements.add(new Point(p_temp));
                p_temp.setLocation(p_temp.getX()+1, p_temp.getY()+1);
            }
            if(Piece.isOnTheBoard(p_temp) 
                    && Board.getPiece(p_temp)!=null
                    && Board.getPiece(p_temp).isWhite() != this.isWhite()){
                possible_movements.add(new Point(p_temp));
            }
        }
        
        //2a diagonal (SUDESTE[Southeast]):
        p_temp = new Point((int) this.getPosition().getX()+1, (int) this.getPosition().getY()-1);
        if(Piece.isOnTheBoard(p_temp)){
            while(Board.getPiece(p_temp) == null && Piece.isOnTheBoard(p_temp)){
                this.possible_movements.add(new Point(p_temp));
                p_temp.setLocation(p_temp.getX()+1, p_temp.getY()-1);
            }
            if(Piece.isOnTheBoard(p_temp) 
                    && Board.getPiece(p_temp)!=null
                    && Board.getPiece(p_temp).isWhite() != this.isWhite()){
                possible_movements.add(new Point(p_temp));
            }
        }
        
        //3a diagonal (SUDOESTE[Southwest]):
        p_temp = new Point((int) this.getPosition().getX()-1, (int) this.getPosition().getY()-1);
        if(Piece.isOnTheBoard(p_temp)){
            while(Board.getPiece(p_temp) == null && Piece.isOnTheBoard(p_temp)){
                this.possible_movements.add(new Point(p_temp));
                p_temp.setLocation(p_temp.getX()-1, p_temp.getY()-1);
            }
            if(Piece.isOnTheBoard(p_temp) 
                    && Board.getPiece(p_temp)!=null
                    && Board.getPiece(p_temp).isWhite() != this.isWhite()){
                possible_movements.add(new Point(p_temp));
            }
        }
        
        //4a diagonal (NOROESTE[Northwest]):
        p_temp = new Point((int) this.getPosition().getX()-1, (int) this.getPosition().getY()+1);
        if(Piece.isOnTheBoard(p_temp)){
            while(Board.getPiece(p_temp) == null && Piece.isOnTheBoard(p_temp)){
                this.possible_movements.add(new Point(p_temp));
                p_temp.setLocation(p_temp.getX()-1, p_temp.getY()+1);
            }
            if(Piece.isOnTheBoard(p_temp) 
                    && Board.getPiece(p_temp)!=null
                    && Board.getPiece(p_temp).isWhite() != this.isWhite()){
                possible_movements.add(new Point(p_temp));
            }
        }
        
        //Movement of the queen to the right - movimento da rainha para a direita
        p_temp = new Point((int) this.getPosition().getX() + 1, (int) this.getPosition().getY());
        if (Piece.isOnTheBoard(p_temp)) {
            while (Board.getPiece(p_temp) == null && Piece.isOnTheBoard(p_temp)) {
                this.possible_movements.add(new Point(p_temp));
                p_temp.setLocation(p_temp.getX() + 1, p_temp.getY());
            }
            if (Piece.isOnTheBoard(p_temp)
                    && Board.getPiece(p_temp) != null
                    && Board.getPiece(p_temp).isWhite() != this.isWhite()){ //test if its a friend or enemy piece - teste para saber se a peca eh amiga ou inimiga
                possible_movements.add(new Point(p_temp));
            }
        }
        
        //Movement of the queen to the left - movimento da rainha para a esquerda
        p_temp = new Point((int) this.getPosition().getX() - 1, (int) this.getPosition().getY());
        if (Piece.isOnTheBoard(p_temp)) {
            while (Board.getPiece(p_temp) == null && Piece.isOnTheBoard(p_temp)) {
                this.possible_movements.add(new Point(p_temp));
                p_temp.setLocation(p_temp.getX() - 1, p_temp.getY());
            }
            if (Piece.isOnTheBoard(p_temp)
                    && Board.getPiece(p_temp) != null
                    && Board.getPiece(p_temp).isWhite() != this.isWhite()){ //test if its a friend or enemy piece - teste para saber se a peca eh amiga ou inimiga
                possible_movements.add(new Point(p_temp));
            }
        }
        
        //Movement of the queen to upward direction - movimento da rainha para a cima
        p_temp = new Point((int) this.getPosition().getX() , (int) this.getPosition().getY() - 1);
        if (Piece.isOnTheBoard(p_temp)) {
            while (Board.getPiece(p_temp) == null && Piece.isOnTheBoard(p_temp)) {
                this.possible_movements.add(new Point(p_temp));
                p_temp.setLocation(p_temp.getX() , p_temp.getY() -1);
            }
            if (Piece.isOnTheBoard(p_temp)
                    && Board.getPiece(p_temp) != null
                    && Board.getPiece(p_temp).isWhite() != this.isWhite()){ //test if its a friend or enemy piece - teste para saber se a peca eh amiga ou inimiga
                possible_movements.add(new Point(p_temp));
            }
        }
        //Movement of the queen to downward direction - movimento da rainha para a baixo
        p_temp = new Point((int) this.getPosition().getX() , (int) this.getPosition().getY() +1);
        if (Piece.isOnTheBoard(p_temp)) {
            while (Board.getPiece(p_temp) == null && Piece.isOnTheBoard(p_temp)) {
                this.possible_movements.add(new Point(p_temp));
                p_temp.setLocation(p_temp.getX() , p_temp.getY()+1);
            }
            if (Piece.isOnTheBoard(p_temp)
                    && Board.getPiece(p_temp) != null
                    && Board.getPiece(p_temp).isWhite() != this.isWhite()){ //test if its a friend or enemy piece - teste para saber se a peca eh amiga ou inimiga
                possible_movements.add(new Point(p_temp));
            }
        }
        
    }

    
    
    
}
