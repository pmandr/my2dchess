package xadrez.model;

import java.awt.Point;
import java.util.ArrayList;
import xadrez.controller.IlegalChessMovement;
import xadrez.controller.Board;

public class Horse extends Piece
{
    public Horse(PieceJSON peca_json) {
        super(peca_json);
    }
    public Horse(Piece piece){
        super(piece);
    }
    public Horse(Point position, boolean is_white) throws IlegalChessMovement {
        super(position, is_white);
        this.setFileName("cavalo_"+(is_white?"branco":"preto")+".png");
        setPieceType("horse");
    }

    

    @Override
    public void calculatePossibleMoves() {
        possible_movements = new ArrayList<Point>(); 
        
        ArrayList<Point> candidate_movements = new ArrayList<Point>(); 
        
        candidate_movements.add(new Point((int) this.getPosition().getX()-1, (int) this.getPosition().getY()+2));
        candidate_movements.add(new Point((int) this.getPosition().getX()+1, (int) this.getPosition().getY()+2));
        candidate_movements.add(new Point((int) this.getPosition().getX()-2, (int) this.getPosition().getY()+1));
        candidate_movements.add(new Point((int) this.getPosition().getX()+2, (int) this.getPosition().getY()+1));
        candidate_movements.add(new Point((int) this.getPosition().getX()-2, (int) this.getPosition().getY()-1));
        candidate_movements.add(new Point((int) this.getPosition().getX()+1, (int) this.getPosition().getY()-2));
        candidate_movements.add(new Point((int) this.getPosition().getX()+2, (int) this.getPosition().getY()-1));
        
        for(int i=0; i<candidate_movements.size(); i++){ 
            if(Piece.isOnTheBoard(candidate_movements.get(i)) 
                && 
               (    Board.getPiece(candidate_movements.get(i))==null 
                    || 
                    Board.getPiece(candidate_movements.get(i)).isWhite()!=Board.isWhiteTurn()
                )){
                    possible_movements.add(new Point(candidate_movements.get(i)));
                }
        }
    }

    
    
   
}
