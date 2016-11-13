
package xadrez.model;

import java.awt.Point;
import java.util.ArrayList;

public class PieceJSON {
      
    private Point position; 
    private ArrayList<Point> posible_moves = new ArrayList<Point>();
    private String filename;
    private String piece_type;
    private boolean is_white; 
    private boolean was_played;
    private boolean is_alive = true;
   
    public PieceJSON importaPeca(Piece p){
        this.setPosition(p.getPosition());
        this.setPossibleMoves(p.getPossibleMovements());
        this.setFilename(p.getFilename());
        this.setPieceType(p.getPieceType());
        this.setIsWhite(p.isWhite());
        this.setWasPlayed(p.wasPlayedWith());
        this.setIsAlive(p.isAlive());
        return this;
    }

    /**
     * @return the position
     */
    public Point getPosicao() {
        return position;
    }

    /**
     * @param posicao the position to set
     */
    public void setPosition(Point posicao) {
        this.position = posicao;
    }

    /**
     * @return the posible_moves
     */
    public ArrayList<Point> getPossibleMovements() {
        return posible_moves;
    }

    /**
     * @param movimentos_possiveis the posible_moves to set
     */
    public void setPossibleMoves(ArrayList<Point> movimentos_possiveis) {
        this.posible_moves = movimentos_possiveis;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param nome_arquivo the filename to set
     */
    public void setFilename(String nome_arquivo) {
        this.filename = nome_arquivo;
    }

    /**
     * @return the piece_type
     */
    public String getPieceType() {
        return piece_type;
    }

    /**
     * @param tipo_peca the piece_type to set
     */
    public void setPieceType(String tipo_peca) {
        this.piece_type = tipo_peca;
    }

    /**
     * @return the is_white
     */
    public boolean isWhite() {
        return is_white;
    }

    /**
     * @param eh_branco the is_white to set
     */
    public void setIsWhite(boolean eh_branco) {
        this.is_white = eh_branco;
    }

    /**
     * @return the was_played
     */
    public boolean wasTouched() {
        return was_played;
    }

    /**
     * @param foi_tocado the was_played to set
     */
    public void setWasPlayed(boolean foi_tocado) {
        this.was_played = foi_tocado;
    }

    /**
     * @return the is_alive
     */
    public boolean isAlive() {
        return is_alive;
    }

    /**
     * @param vivo the is_alive to set
     */
    public void setIsAlive(boolean vivo) {
        this.is_alive = vivo;
    }
}
