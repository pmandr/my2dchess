
package xadrez.view;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import xadrez.Main;
import xadrez.controller.Board;
import xadrez.model.Piece;

/**
 *
 * @author Paulo
 */
public class BoardCanvas extends JPanel
{
    private static int squareSize;
    private final String images_path = "xadrez/images/";
    
    public BoardCanvas() throws Exception
    {
        super();
        Board.initializeBoard();
    }

    @Override //override paintComponent function from class JPanel - sobrescrita do metodo paintComponent da classe JPanel
    protected void paintComponent(Graphics g)
    {
        //super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.WHITE);
        g2.setColor(Color.LIGHT_GRAY);

        float maxWidth = this.getWidth();
        float maxHeight = this.getHeight();
        float boardSize = (maxWidth < maxHeight) ? maxWidth : maxHeight;
        squareSize = Math.round(boardSize/8.0f);
                
        //Fills board with black and white squares - preenche tabuleiro com casas pretas e brancas
        for(int i = 0; i<8; ++i){
            for(int j = 0; j<8; ++j){
                //dwitch square color - varia a cor do quadrante
                if (g2.getColor() == Color.WHITE)
                {
                    g2.setColor(Color.LIGHT_GRAY);
                } else
                {
                    g2.setColor(Color.WHITE);
                }

                //Draws a square on the board - Desenha um quadrado do tabuleiro
                g2.fillRect(i*squareSize,j*squareSize,(i*squareSize)+squareSize, (j*squareSize)+squareSize);
            }

            if (g2.getColor() == Color.WHITE)
            {
                g2.setColor(Color.LIGHT_GRAY);
            } else
            {
                g2.setColor(Color.WHITE);
            }
        }
        
        
        //Draws a piece (or not) - desenha peca (ou não)
        for(int i = 0; i<8; ++i){
            for(int j = 0; j<8; ++j){
                Piece peca_temp = Board.getPiece(i+1, j+1);
                if(peca_temp != null){
                    //Test if the piece is currently selected - testa se é a peca selecionada no momento
                    if(Board.getOriginPosition() != null 
                            && peca_temp.getPosition().getX() == Board.getOriginPosition().getX()
                            && peca_temp.getPosition().getY() == Board.getOriginPosition().getY()
                            ){
                        //colors the background of the currently selected piece - colore o fundo da peca selecionada no momento
                        g2.setColor(Color.GREEN);
                        g2.fillRect(i*squareSize,j*squareSize,squareSize, squareSize);
                        
                        //colors the possible moves of the piece - colore os movimentos possiveis desta peca
                        peca_temp.calculatePossibleMoves();
                        for(int k = 0; k < peca_temp.getPossibleMovements().size(); k++){
                            Point movimento_possivel = peca_temp.getPossibleMovements().get(k);
                            g2.setColor(Color.YELLOW);
                            g2.fillRect(
                                    (int) (movimento_possivel.getX()-1)*squareSize,
                                    (int) (movimento_possivel.getY()-1)*squareSize,
                                    (int) squareSize,
                                    (int) squareSize);
                        }
                    }
                    
                    try{
                        this.drawImage(g, this.images_path+peca_temp.getFilename(), i, j);
                    }catch(IOException ex){
                        System.out.printf("IO Exception: "+ex.getMessage());
                        
                        // draws alternative image - desenha imagem alternativa
                        
                        //Draws shape (circle) - DESENHA FORMA (CIRCULO)
                        if(peca_temp.isWhite()){
                            g2.setColor(Color.ORANGE);
                        }else{
                            g2.setColor(Color.BLACK);
                        }
                        g2.fillOval(i*squareSize+8,j*squareSize+8,squareSize-16, squareSize-16);
                        
                        //draws text - desenha texto
                        int text_x_offset = squareSize/2 -12;
                        int text_y_offset = squareSize/2 +4;
                        if(peca_temp.isWhite()){
                            g2.setColor(Color.BLACK);
                        }else{
                            g2.setColor(Color.WHITE);
                        }
                        FontMetrics fm = g.getFontMetrics(g.getFont());
                        g2.drawString(peca_temp.getMyTag(), i*squareSize + text_x_offset, (j*squareSize)+text_y_offset);
                        
                    }
                }
            }
        }
        
        
    }
    
    public static int getSquareSize(){
        return squareSize;
    }

    private void drawImage(Graphics g, String image_location, int i, int j) throws IOException {
        ImageIcon image_temp = new ImageIcon(getClass().getClassLoader().getResource(image_location));
        switch(image_temp.getImageLoadStatus()){
            case MediaTracker.COMPLETE:
                System.out.println("Image Loaded OK");
                Image image = image_temp.getImage();
                g.drawImage(image, i*squareSize, j*squareSize, 80, 80, null);
                break;
            default:
                System.out.println("Image was not successfully loaded");
                System.out.println(image_location);
                throw new IOException("Image not found in: " + image_location);
        }
    }

}
