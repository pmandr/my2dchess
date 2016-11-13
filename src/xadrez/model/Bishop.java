package xadrez.model;

import java.awt.Point;
import java.util.ArrayList;
import xadrez.controller.IlegalChessMovement;
import xadrez.controller.Board;

public class Bishop extends Piece
{
    public Bishop(PieceJSON json_piee) {
        super(json_piee);
    }
    public Bishop(Piece piece){
        super(piece);
    }
    public Bishop(Point position, boolean is_white) throws IlegalChessMovement {
        super(position, is_white);
        this.setFileName("bispo_"+(is_white?"branco":"preto")+".png");
        setPieceType("bishop");
    }
    

    

    @Override
    public void calculatePossibleMoves() {
        this.possible_movements = new ArrayList<Point>();
        Point p_temp; //Ponto que será usado para percorrer trajetos - Point to be used for pass by the paths
        
        //1a diagonal (NORDESTE) - 1st diagonal (Northeast)
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
        
    }

   
   
}
