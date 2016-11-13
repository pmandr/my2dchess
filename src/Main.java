//PROFESSOR: JOAO BATISTA
//DISCIPLINA DE PROGRAMACAO ORIENTADA A OBJETOS
//USP - ICMC SAO CARLOS


package xadrez;

import java.util.logging.Level;
import java.util.logging.Logger;
import xadrez.view.GameWindow;

public class Main
{
    private static GameWindow frame;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    frame = new GameWindow();
                }catch(Exception ex){
                    System.out.println("error on initialization!");
                }
                    frame.setVisible(true);
                    frame.refresh();
            }
        });
    }
    
    public static GameWindow getFrame(){
        return frame;
    }

}
