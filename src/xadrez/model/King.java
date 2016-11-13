package xadrez.model;

import java.awt.Point;
import java.util.ArrayList;
import xadrez.controller.IlegalChessMovement;
import xadrez.controller.Board;
import static xadrez.model.Piece.isOnTheBoard;

public class King extends Piece {
    public King(PieceJSON piece_json) {
        super(piece_json);
    }
    public King(Piece piece){
        super(piece);
    }
    public King(Point position, boolean is_white) throws IlegalChessMovement {
        super(position, is_white);
        this.setFileName("rei_" + (is_white ? "branco" : "preto") + ".png");
        setPieceType("king");
    }

    

    @Override
    public void calculatePossibleMoves() {
        this.possible_movements = new ArrayList<Point>();
        Point p_aux;

        p_aux = new Point((int) this.getPosition().getX() + 1, (int) this.getPosition().getY());
        if ((Board.getPiece(p_aux) == null || Board.getPiece(p_aux).isWhite() != Board.isWhiteTurn()) 
                && isOnTheBoard(p_aux)) {
            this.possible_movements.add(p_aux);
        }

        p_aux = new Point((int) this.getPosition().getX() + 1, (int) this.getPosition().getY() + 1);
        if ((Board.getPiece(p_aux) == null || Board.getPiece(p_aux).isWhite() != Board.isWhiteTurn())
                && isOnTheBoard(p_aux)) {
            this.possible_movements.add(p_aux);
        }

        p_aux = new Point((int) this.getPosition().getX() + 1, (int) this.getPosition().getY() - 1);
        if ((Board.getPiece(p_aux) == null || Board.getPiece(p_aux).isWhite() != Board.isWhiteTurn())
                && isOnTheBoard(p_aux)) {
            this.possible_movements.add(p_aux);
        }

        p_aux = new Point((int) this.getPosition().getX(), (int) this.getPosition().getY() - 1);
        if ((Board.getPiece(p_aux) == null || Board.getPiece(p_aux).isWhite() != Board.isWhiteTurn())
                && isOnTheBoard(p_aux)) {
            this.possible_movements.add(p_aux);
        }

        p_aux = new Point((int) this.getPosition().getX(), (int) this.getPosition().getY() + 1);
        if ((Board.getPiece(p_aux) == null || Board.getPiece(p_aux).isWhite() != Board.isWhiteTurn())
                && isOnTheBoard(p_aux)) {
            this.possible_movements.add(p_aux);
        }

        p_aux = new Point((int) this.getPosition().getX() - 1, (int) this.getPosition().getY() + 1);
        if ((Board.getPiece(p_aux) == null || Board.getPiece(p_aux).isWhite() != Board.isWhiteTurn())
                && isOnTheBoard(p_aux)) {
            this.possible_movements.add(p_aux);
        }

        p_aux = new Point((int) this.getPosition().getX() - 1, (int) this.getPosition().getY() - 1);
        if ((Board.getPiece(p_aux) == null || Board.getPiece(p_aux).isWhite() != Board.isWhiteTurn())
                && isOnTheBoard(p_aux)) {
            this.possible_movements.add(p_aux);
        }

        p_aux = new Point((int) this.getPosition().getX() - 1, (int) this.getPosition().getY());
        if ((Board.getPiece(p_aux) == null || Board.getPiece(p_aux).isWhite() != Board.isWhiteTurn())
                && isOnTheBoard(p_aux)) {
            this.possible_movements.add(p_aux);
        }
    }
}
