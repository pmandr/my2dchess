package xadrez.controller;

import java.awt.Point;

/**
 *
 * @author Paulo
 */
public class IlegalChessMovement extends Exception{
    private Point origin, destination;
    
    public IlegalChessMovement() {
        this("Movement not allowed",null,null);
    }
    public IlegalChessMovement(String msg) {
        this(msg,null,null);
    }
    public IlegalChessMovement(Point origem, Point destino) {
        this("Movement not allowed",origem, destino);
    }
    public IlegalChessMovement(String msg, Point origem, Point destino) {
        super(msg);
        this.origin = origem;
        this.destination = destino;
    }

}
