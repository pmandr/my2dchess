package xadrez.model;

import com.google.gson.internal.LinkedTreeMap;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import xadrez.controller.IlegalChessMovement;

public abstract class Piece 
{
    private Point position; 
    protected ArrayList<Point> possible_movements = new ArrayList<Point>();
    private boolean was_played_with;
    private boolean is_alive = true;
    private boolean is_white; 
    private String nome_arquivo;
    private String piece_type;
    
    public static Piece pieceFactory(PieceJSON peca_json) {
        Piece piece_return;
        switch(peca_json.getPieceType()){
            case "tower":
                piece_return = new Tower(peca_json);
//                Tower peca_return = new Tower(peca_json);
                return piece_return;
            case "horse":
                piece_return = new Horse(peca_json);
                return piece_return;
            case "bishop":
                piece_return = new Bishop(peca_json);
                return piece_return;
            case "king":
                piece_return = new King(peca_json);
                return piece_return;
            case "queen":
                piece_return = new Queen(peca_json);
                return piece_return;
            case "pawn":
                piece_return = new Pawn(peca_json);
                return piece_return;
            default:
                return null;
        }
    }
    public Piece(){
        super();
    }
    public Piece(PieceJSON piece_to_be_copied){
        
        this.setWhite(piece_to_be_copied.isWhite());
        this.setFileName(piece_to_be_copied.getFilename());
        try {
            this.setPosition(piece_to_be_copied.getPosicao());
        } catch (IlegalChessMovement ex) {
            Logger.getLogger(Piece.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setPieceType(piece_to_be_copied.getPieceType());
        this.possible_movements = piece_to_be_copied.getPossibleMovements();
        this.was_played_with = piece_to_be_copied.wasTouched();
        this.is_alive = piece_to_be_copied.isAlive();
    }
    
    public Piece(Piece piece_to_be_copied){
        this.position = new Point(piece_to_be_copied.getPosition());
        for(Point p: piece_to_be_copied.getPossibleMovements()){
            this.possible_movements.add(new Point(p));
        }
//        this.foi_tocado = peca_para_ser_copiada.foiTocado();
//        this.is_alive = peca_para_ser_copiada.isAlive();
        this.is_white = piece_to_be_copied.isWhite();
        this.nome_arquivo = piece_to_be_copied.getFilename();
    }
    public Piece(Point position, boolean is_white) throws IlegalChessMovement
    {
        if(!isOnTheBoard(position))
        {
            throw new IlegalChessMovement("Invalid position. You attempted to create a piece in a position that is out of the board.");
        }
        
         this.position = position;
         this.is_white = is_white;
    }
    
    public void setPosition(Point posicao) throws IlegalChessMovement
    {
        if(!isOnTheBoard(posicao))
        {
            throw new IlegalChessMovement("Invalid position. You attempted to create a piece in a position that is out of the board.");
        }

        this.position = posicao;
        
        
    }
    
    public Point getPosition()
    {
        return this.position;
    }
    
    
    
    public void setWhite(boolean is_white)
    {
        this.is_white = is_white;
    }
    
    public boolean isWhite()
    {
        return this.is_white;
    }
    
    
    public static boolean isOnTheBoard(Point position)
    {
        if(position.getX() < 1 || position.getX() > 8)
            return false;
        if(position.getY() < 1 || position.getY() > 8)
            return false;
        return true;
    }
    
    void die()
    {
        is_alive = false;
    }
    
    
    public boolean isAllowedMove(Point position){
//        return this.possible_movements.contains(posicao);
        if(this.possible_movements.contains(position)){
            return true;
        }else{
            return false;
        }
    }
    
    public void setFileName(String nome){
        this.nome_arquivo = nome;
    }
    public String getFilename(){
        return this.nome_arquivo;
    }
    public ArrayList<Point> getPossibleMovements(){
        return this.possible_movements;
    }
    public abstract void calculatePossibleMoves();
    
    public static String getPieceTag(Piece peca) {
        if(peca.getClass().equals(Pawn.class)){
            return "Pawn";
        }
        if(peca.getClass().equals(Tower.class)){
            return "Tower";
        }
        if(peca.getClass().equals(Horse.class)){
            return "Horse";
        }
        if(peca.getClass().equals(Bishop.class)){
            return "Bishop";
        }
        if(peca.getClass().equals(Queen.class)){
            return "Queen";
        }
        if(peca.getClass().equals(King.class)){
            return "King";
        } else
            return "Unknown";
    }
    public String getMyTag() {
        return Piece.getPieceTag(this);
    }

    protected String getPieceType() {
        return piece_type;
    }
    protected void setPieceType(String piece_type) {
        this.piece_type = piece_type;
    }

    boolean wasPlayedWith() {
        return this.was_played_with;
    }

    boolean isAlive() {
        return this.is_alive;
    }
}
