
package xadrez.view;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import xadrez.controller.IlegalChessMovement;
import xadrez.controller.Board;
import xadrez.Main;

/**
 *
 * @author Paulo
 */
public class Listener implements MouseListener, MouseMotionListener, ActionListener{

    Listener() {
    }
 @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int index_X = (int) Math.floor(e.getX()/BoardCanvas.getSquareSize());
        int index_Y = (int) Math.floor(e.getY()/BoardCanvas.getSquareSize());
        
        System.out.println("X: " + ++index_X + "\tY: " + ++index_Y);
        GameWindow.getSystemLog().writeInFile("Board position ("+index_X+","+index_Y+") selected", "info");
//        GameWindow.getSystemLog().writeInFile("Coordenada de tabuleiro ("+index_X+","+index_Y+") selecionada", "info");
        try {
            if(Board.getOriginPosition()==null){
                Board.setOriginPosition(new Point(index_X,index_Y));
            }else{
                Board.setDestinationPosition(new Point(index_X,index_Y));
            }
        } catch (IlegalChessMovement ex) {
            GameWindow.getSystemLog().writeInFile("Movement not allowed", "warning");
//            GameWindow.getSystemLog().writeInFile("Movimento não permitido.", "warning");
            JOptionPane.showMessageDialog(Main.getFrame(),
                ex.getMessage(),
                "Ok",
                JOptionPane.OK_OPTION);
        } catch (Exception ex) {
            GameWindow.getSystemLog().writeInFile("Unindentified error on piece movement.", "error");
//            GameWindow.getSystemLog().writeInFile("Erro não identificado ao movimentar peça.", "error");
            JOptionPane.showMessageDialog(Main.getFrame(),
                "Erro : Message:"+ex.getMessage()+" | StackTrace: "+ex.getStackTrace().toString(),
                "Ok",
                JOptionPane.OK_OPTION);
        }
        Main.getFrame().refresh();
        GameWindow.board.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //funcionalidades do botao DEITAR O REI - Functionalities of the button "Give up"
        GameWindow.getSystemLog().writeInFile("Opening confirmation about giving up.", "error");
//        GameWindow.getSystemLog().writeInFile("Abrindo caixa de confirmação sobre deitar o rei.", "error");
        Object[] options = {"Sim",
                    "Não"};
        int n = JOptionPane.showOptionDialog(Main.getFrame(),
            "Are you sure you want to give up and LOSE THE GAME?",
//            "Você tem certeza que quer deitar o seu rei e PERDER O JOGO?",
            "Give up",
//            "Deitar o Rei",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,     //do not use a custom Icon
            options,  //the titles of buttons
            options[1]); //default button title
        
        
        if(n == JOptionPane.YES_OPTION){
            GameWindow.getSystemLog().writeInFile("Confirmation received for giving up. The player "+(Board.isWhiteTurn()?"WHITE":"BLACK")+" HAS GIVEN UP AND LOST!", "error");
//            GameWindow.getSystemLog().writeInFile("Confirmação recebida para deitar o rei. O JOGADOR "+(Board.isWhiteTurn()?"BRANCO":"PRETO")+" DESISTIU E PERDEU!", "error");
            JOptionPane.showMessageDialog(Main.getFrame(),
                "THE PLAYER "+(Board.isWhiteTurn()?"WHITE":"BLACK")+" HAS GIVEN UP AND LOST!",
//                "O JOGADOR "+(Board.isWhiteTurn()?"BRANCO":"PRETO")+" DESISTIU E PERDEU!",
                "Ok",
                JOptionPane.OK_OPTION);
            
            System.out.println("You gave up and lost!!!");
//            System.out.println("Perdeu!!!");
            Main.getFrame().dispose();
            Main.main(new String[0]);
            
//            System.exit(0);
        }else{
            GameWindow.getSystemLog().writeInFile("Confirmation denied for giving up.", "error");
            System.out.println("Attaboy. Don't give up!!!");
//            GameWindow.getSystemLog().writeInFile("Confirmação negada para deitar o rei.", "error");
//            System.out.println("Obrigado por não desistir!!!");
        }
    }
}
