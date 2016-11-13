package xadrez.model;

import java.awt.Point;
import java.util.ArrayList;
import xadrez.controller.IlegalChessMovement;
import xadrez.controller.Board;

public class Pawn extends Piece
{
   public Pawn(PieceJSON piece_json) {
        super(piece_json);
    }
    public Pawn(Piece piece){
        super(piece);
    }
    public Pawn(Point position, boolean is_white) throws IlegalChessMovement
    {
        super(position, is_white); //chama o construtor pai
        this.setFileName("peao_"+(is_white?"branco":"preto")+".png");
        setPieceType("pawn");
    }
    
    

    @Override
    public void calculatePossibleMoves() { 
        this.possible_movements = new ArrayList<Point>();
        Point p_aux;
        
       //for white pawns - peoes de baixo (brancos)
        if(this.isWhite()){
           //Test if possible move 1 unit forward -  teste sobre andar 1 casa para frente
           p_aux = new Point((int) this.getPosition().getX()+1, (int) this.getPosition().getY());
           if(Board.getPiece(p_aux)==null && isOnTheBoard(p_aux)){
               this.possible_movements.add(p_aux);

               //Test if possible move 2 units forward - teste sobre andar 2 casa para frente
               p_aux = new Point((int) this.getPosition().getX()+2, (int) this.getPosition().getY());
               if(this.getPosition().getX()==2 && Board.getPiece(p_aux)==null && isOnTheBoard(p_aux)){
                   this.possible_movements.add(p_aux);
               }
           }
            
           //Test northeast diagonal - testa diagonal nordeste
           p_aux = new Point((int) this.getPosition().getX()+1, (int) this.getPosition().getY()+1);
           if(Board.getPiece(p_aux)!=null && !Board.getPiece(p_aux).isWhite() && isOnTheBoard(p_aux)){
               this.possible_movements.add(p_aux);
           }
           
           //Test southeast diagonal - testa diagonal sudeste
           p_aux = new Point((int) this.getPosition().getX()+1, (int) this.getPosition().getY()-1);
           if(Board.getPiece(p_aux)!=null && !Board.getPiece(p_aux).isWhite() && isOnTheBoard(p_aux)){
               this.possible_movements.add(p_aux);
           }
        }else{//code for black pawns
           //Test if possible move 1 unit forward - teste sobre andar 1 casa para frente
           p_aux = new Point((int) this.getPosition().getX()-1, (int) this.getPosition().getY());
           if(Board.getPiece(p_aux)==null && isOnTheBoard(p_aux)){
               this.possible_movements.add(p_aux);

               //Test if possible move 2 units forward - teste sobre andar 2 casa para frente
               p_aux = new Point((int) this.getPosition().getX()-2, (int) this.getPosition().getY());
               if(this.getPosition().getX()==7 && Board.getPiece(p_aux)==null && isOnTheBoard(p_aux)){
                   this.possible_movements.add(p_aux);
               }
           }
            
           //Test southeast diagonal - testa diagonal sudoeste
           p_aux = new Point((int) this.getPosition().getX()-1, (int) this.getPosition().getY()+1);
           if(Board.getPiece(p_aux)!=null && Board.getPiece(p_aux).isWhite() && isOnTheBoard(p_aux)){
               this.possible_movements.add(p_aux);
           }
           
           //test northeast diagonal - testa diagonal noroeste
           p_aux = new Point((int) this.getPosition().getX()-1, (int) this.getPosition().getY()-1);
           if(Board.getPiece(p_aux)!=null && Board.getPiece(p_aux).isWhite() && isOnTheBoard(p_aux)){
               this.possible_movements.add(p_aux);
           }
            
        }
       
    }
}
    
    

