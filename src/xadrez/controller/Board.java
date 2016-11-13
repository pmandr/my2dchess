package xadrez.controller;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import xadrez.model.*;
import xadrez.view.GameWindow;
import xadrez.Main;

public class Board {

    private static ArrayList<ArrayList> positions;
    private static ArrayList<ArrayList> positions_backup;
    private static boolean is_in_undo_state = false;
    private static boolean is_white_turn;// = true;
    private static Point selected_position_origin;// = null;
    private static int no_of_plays = 0;
    private static int game_time = 0;
    private static int player_time = 0;
    private static Timer timer;
    
    public static void initializeBoard() throws Exception{
        GameWindow.getSystemLog().writeInFile("Initializing board...", "info");
        timer = new Timer();
        positions = new ArrayList<ArrayList>();
        positions_backup = new ArrayList<ArrayList>();
        is_white_turn = true;
        selected_position_origin = null;

        Board.initializePositions();
        initializePieces();
    }
            
    public static Point getOriginPosition() {
        return selected_position_origin;
    }

    public static void setOriginPosition(Point position) throws IlegalChessMovement {
        if (Board.getPiece(position)== null //tem alguma peca nessa posicao? - Is there any piece in this position?
                || 
            Board.getPiece(position).isWhite() != is_white_turn //essa peca eh minha?  - Is this piece mine?
                ) {
            throw new IlegalChessMovement("Movement not allowed");
        }
        System.out.println("Piece in position ("+position.getX()+","+position.getY()+") selected");
        Board.getPiece(position).calculatePossibleMoves();
        selected_position_origin = position;
    }

    public static void setDestinationPosition(Point clicked_position) throws IlegalChessMovement, Exception {
        Piece piece = Board.getPiece(clicked_position);
        
        if(getOriginPosition() == null){ //a origem já foi selecionada?
            throw new IlegalChessMovement("Movement not allowed");
        }
        if (Board.getPiece(clicked_position) != null 
                && Board.getPiece(getOriginPosition()).isWhite() == Board.getPiece(clicked_position).isWhite()) { //selecionei uma peca minha de novo
            setOriginPosition(clicked_position);
            return;
        } 
        if (!Board.getPiece(getOriginPosition()).isAllowedMove(clicked_position)) { //peca com habilidade de me mover pra ca - Piece with the hability to move here
            throw new IlegalChessMovement("Movement not allowed", getOriginPosition(), clicked_position);
        } 
        
         //faz backup antes de efetivar a jogada - Do game backup before effectivate the play
        backup();
        is_in_undo_state = false;
        no_of_plays++;
        
        Piece origin_piece = Board.getPiece(getOriginPosition());
        Piece pdestino = Board.getPiece(clicked_position);
        
        origin_piece.setPosition(clicked_position);
        Board.setPiece(origin_piece, clicked_position); //coloca a peca de origem no destino - Play the piece from the origin to the destination
        Board.setPiece(null, getOriginPosition()); //agora a posicao de origem da peca fica vazia - Make the origin position empty
        
        if(isCurrentPlayerInCheck(positions)){
            //o jogador atual SE colocou em cheque - The current player has put himself in check
            JOptionPane.showMessageDialog(Main.getFrame(),
                "You can't put yourself (or continue) in check!",
//                "Você não pode se colocar (ou continuar) em Cheque!",
                "Ok",
                JOptionPane.OK_OPTION);
            
            //reverte modificações - Revert modifications
            Board.setPiece(origin_piece, getOriginPosition());
            Board.setPiece(pdestino, clicked_position);
            no_of_plays--;
            backup();//caso o jogador tenha feito a jogada e se colcado em cheque, essa jogada eh invalida. Portanto este comando REFAZ O BACKUP  e elimina a jogada invalida recem feita. Mas backup fica com a jogada atual, e não com a anterior. Portanto neste caso o undo nao funcionará mais (apenas para essa jogada).
            origin_piece.setPosition(getOriginPosition());
        }else{
            //eh jogada valida => efetiva a jogada E testa se o adversario foi colocado em cheque
            //It's a valid move => Effectivate the play AND test if the oponent has been put in check
            resetPlayerClock();
            Main.getFrame().appendToLog((is_white_turn?"White":"Black")+Piece.getPieceTag(origin_piece)+" moved "
                    + "from "+getPositionLabel(getOriginPosition())+" to "+getPositionLabel(clicked_position));
            GameWindow.getSystemLog().writeInFile((is_white_turn?"White":"Black")+Piece.getPieceTag(origin_piece)+" moved "
                    + "from "+getPositionLabel(getOriginPosition())+" to "+getPositionLabel(clicked_position), "info");
            is_white_turn = !is_white_turn; //muda jogador - Change player turn
            selected_position_origin = null;
            
            if(isCurrentPlayerInCheck(positions) && isCurrentPlayerInCheckMate(positions)){
                GameWindow.getSystemLog().writeInFile("Check mate! End of game! The player"+(!is_white_turn?" WHITE":" BLACK")+" won!", "info");
//                GameWindow.getSystemLog().writeInFile("Cheque Mate! Fim do jogo! O jogador"+(!is_white_turn?" BRANCO":" PRETO")+" ganhou!", "info");
                JOptionPane.showMessageDialog(Main.getFrame(),
                    "Check mate! End of game! The player"+(!is_white_turn?" WHITE":" BLACK")+" won!",
//                    "Cheque Mate! Fim do jogo! O jogador"+(!is_white_turn?" BRANCO":" PRETO")+" ganhou!",
                    "Ok",
                    JOptionPane.OK_OPTION);
                System.out.println("CHECK MATE!!!!!!");
//                System.out.println("CHEQUE MATE!!!!!!");
                Main.getFrame().dispose();
                Main.main(new String[0]);
            }
            else if(isCurrentPlayerInCheck(positions)){
                GameWindow.getSystemLog().writeInFile("The player"+(!is_white_turn?" WHITE":" BLACK")+" has put his oponent in CHECK!", "info");
//                GameWindow.getSystemLog().writeInFile("O jogador"+(!is_white_turn?" BRANCO":" PRETO")+" colocou seu adversário em CHEQUE!", "info");
                JOptionPane.showMessageDialog(Main.getFrame(),
                    "Check!",
                    "Ok",
                    JOptionPane.OK_OPTION);
                Main.getFrame().appendToLog("CHECK!");
//                Main.getFrame().appendToLog("CHEQUE!");
            }
        }
        
//        getPiece(getOriginPosition()).setPosition(posicao_clicada); //fala pra peça que ela esta em outra posicao
    }

    private static void initializePieces() throws Exception {
        ArrayList column1 = (ArrayList) positions.get(1);
        column1.set(1, new Tower(new Point(1, 1), true));
        column1.set(2, new Horse(new Point(1, 2), true));
        column1.set(3, new Bishop(new Point(1, 3), true));
        column1.set(4, new Queen(new Point(1, 4), true));
        column1.set(5, new King(new Point(1, 5), true));
        column1.set(6, new Bishop(new Point(1, 6), true));
        column1.set(7, new Horse(new Point(1, 7), true));
        column1.set(8, new Tower(new Point(1, 8), true));

        ArrayList column2 = (ArrayList) positions.get(2);
        for (int i = 1; i <= 8; i++) {
            column2.set(i, new Pawn(new Point(2, i), true));
        }

        ArrayList column8 = (ArrayList) positions.get(8);
        column8.set(1, new Tower(new Point(8, 1), false));
        column8.set(2, new Horse(new Point(8, 2), false));
        column8.set(3, new Bishop(new Point(8, 3), false));
        column8.set(4, new Queen(new Point(8, 4), false));
        column8.set(5, new King(new Point(8, 5), false));
        column8.set(6, new Bishop(new Point(8, 6), false));
        column8.set(7, new Horse(new Point(8, 7), false));
        column8.set(8, new Tower(new Point(8, 8), false));

        ArrayList column7 = (ArrayList) positions.get(7);
        for (int i = 1; i <= 8; i++) {
            column7.set(i, new Pawn(new Point(7, i), false));
        }

    }

    private static void initializePositions() {
        Board.initializePositions(positions);
        Board.initializePositions(positions_backup);
    }
    public  static void initializePositions(ArrayList<ArrayList> positions) {   
        //criar linhas (vazias) - Create empty rows
        positions.add(null);

        for (int i = 1; i <= 8; i++) {
            if(positions.size()==9){ 
                //ja foi iniciado e esta carregando - Has already been initialized and it's being loaded
                positions.set(i, new ArrayList<Piece>());
            }else{
                positions.add(new ArrayList<Piece>());
            }
        }

        for (int i = 1; i <= 8; i++) {
            ArrayList<Piece> linha = positions.get(i);
            for (int j = 0; j <= 8; j++) {
                if(linha.size()==9){
                    linha.set(j, null);
                }else{
                    linha.add(null);
                }
            }
        }
    }

    public static Piece getPiece(Point p) {
        if(Piece.isOnTheBoard(p)){
            return (Piece) positions.get((int) p.getX()).get((int) p.getY());
        }else{
            return null;
        }
    }
    public static Piece getPiece(int x, int y) {
        return (Piece) positions.get(x).get(y);
    }

    public static boolean isWhiteTurn() {
        return is_white_turn;
    }

    private static void setPiece(Piece piece, Point position_in_board) {
        Board.positions
                .get((int) position_in_board.getX()) //pega a linha inteira - Gets the whole row
                .set((int) position_in_board.getY(), piece); 
    }
    
    public static void calculatePossibleMovementsOfAllPieces(ArrayList<ArrayList> board){
        GameWindow.getSystemLog().writeInFile("Calculating possible moves for all the pieces...", "info");
//        GameWindow.getSystemLog().writeInFile("Calculando movimetos possiveis de todas as peças...", "info");
        Piece p_aux;
        for(int i=1;i<=8;i++){
            for(int j=1;j<=8;j++){
                p_aux = (Piece) board.get(i).get(j);
                if(p_aux!=null){
                    p_aux.calculatePossibleMoves();
                }
            }
        }
    }
    
    
    public static boolean isCurrentPlayerInCheck(ArrayList<ArrayList> board) throws Exception{
        GameWindow.getSystemLog().writeInFile("Testing if the current player is in check...", "info");
//        GameWindow.getSystemLog().writeInFile("Testando se o jogador atual está em cheque...", "info");
            
        Piece p_aux;
        
        Board.calculatePossibleMovementsOfAllPieces(board);
        
        //localiza rei do jogador atual - Locate the king of the curent player
        Point king_position = null;
        for(int i=1;i<=8;i++){
            for(int j=1;j<=8;j++){
                p_aux = (Piece) board.get(i).get(j);
                if(p_aux!=null //checa se eh uma casa nao vazia - Check if it's a non empty place
                        && p_aux.isWhite()==is_white_turn //eh peca do Jogador da vez atual? - Is it a piece of the curent player?
                        && p_aux instanceof King  //eh o King? - Is it the king?
                        ){
                    king_position = p_aux.getPosition();
                }
            }
        }
        if(king_position==null){
            GameWindow.getSystemLog().writeInFile("The position of the "+(is_white_turn?"white":"black")+" King was not found. Throwing exception...", "error");
//            GameWindow.getSystemLog().writeInFile("Posicao do Rei de "+(is_white_turn?"Branco":"Preto")+" nao encontrada. Lançando Exception...", "error");
            throw new Exception("Position of the "+(is_white_turn?"white":"black")+" King not found");
//            throw new Exception("Posicao do Rei de "+(is_white_turn?"Branco":"Preto")+" nao encontrada");
        }
        
        //checa se existe alguma peca do oponente que contem a posicao do rei como um de seus movimentos possiveis
        //check if there is any oponent piece that has the current player's king as a possible movement
        for(int i=1;i<=8;i++){
            for(int j=1;j<=8;j++){
                p_aux = (Piece) board.get(i).get(j);
                if(p_aux!=null //checa se eh uma casa nao vazia - Is it a non empty place?
                        && p_aux.isWhite()!=is_white_turn //eh peca do Jogador da vez atual? - Is it a piece of the current player?
                        ){
                    if(p_aux.getPossibleMovements().contains(king_position) == true){
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

    private static String getPositionLabel(Point position) {
        String y_letter = "";
        switch((int)position.getY()){
            case 1: y_letter = "A"; break;
            case 2: y_letter = "B"; break;
            case 3: y_letter = "C"; break;
            case 4: y_letter = "D"; break;
            case 5: y_letter = "E"; break;
            case 6: y_letter = "F"; break;
            case 7: y_letter = "G"; break;
            case 8: y_letter = "H"; break;
        }
        return "("+y_letter+(int)position.getX()+")";
    }

    private static boolean isCurrentPlayerInCheckMate(ArrayList<ArrayList> board) {
        //ESSA FUNÇÃO SOH SERA CHAMADA CASO O JOGADOR ATUAL JA ESTIVER EM CHEQUE
        //THIS FUNCTION WILL BE CALLED IF THE CURRENT PLAYER IS ALREADY IN CHECK
        
        GameWindow.getSystemLog().writeInFile("Testing if the current player is in check mate...", "info");
//        GameWindow.getSystemLog().writeInFile("Testando se o jogador atual está em cheque mate...", "info");
        Piece p_aux;
        
        //se houver qualquer simulacao em que o jogador SAIA do CHEQUE, CHEQUE_MATE = FALSO
        //If there's any possibility for the current player to NOT BE in check mate, then check_mate = false
        boolean check_mate = true; 
        
        Board.calculatePossibleMovementsOfAllPieces(board);
        
        //simula todos os movimentos possiveis das peças do jogador atual - Simulate all the possible moves of the current player's pieces
        Point peca_aux = null;
        for(int i=1;i<=8;i++){
            for(int j=1;j<=8;j++){
                p_aux = (Piece) board.get(i).get(j);
                if(p_aux!=null //checa se eh uma casa nao vazia - check if its an non empty place
                        && p_aux.isWhite()==is_white_turn //eh peca do Jogador da vez atual? - Is it a piece of the current player?
                        ){
                    //testa todos os movimentos possiveis desta peca - Test all the possible moves of this piece
                    for (Point movimento_possivel : p_aux.getPossibleMovements()) {
                        //salva valores originais - save the original values
                        Piece peca_origem = p_aux;
                        Point pos_origem = p_aux.getPosition();
                        Piece peca_destino = Board.getPiece(movimento_possivel);
                        Point pos_destino = movimento_possivel;
                        
                        //simula mudanca - simulate change
			Board.setPiece(peca_origem, pos_destino);
			Board.setPiece(null, pos_origem);
                        try {
                            peca_origem.setPosition(pos_destino);
                        } catch (Exception ex) {
                            GameWindow.getSystemLog().writeInFile("Error in simulating movement of the piece to check if check mate", "error");
                            System.out.println("Error in simulating movement of the piece to check if check mate");
//                            GameWindow.getSystemLog().writeInFile("Erro ao simular movimento de peça para checar CHEQUE MATE", "error");
//                            System.out.println("Erro ao simular movimento de peça para checar CHEQUE MATE");
                        }
                        
                        //testa se há saída para o CHEQUE ATUAL - Check if there's any way out for the current CHECK
                        try {
                            if(!isCurrentPlayerInCheck(board)) check_mate = false;
                        } catch (Exception ex) {
                            GameWindow.getSystemLog().writeInFile("Error when testing IF CHECK for testing IF CHECK MATE", "error");
                            System.out.println("Error when testing IF CHECK for testing IF CHECK MATE");
//                            GameWindow.getSystemLog().writeInFile("Erro ao testar CHEQUE para checar CHEQUE MATE", "error");
//                            System.out.println("Erro ao testar CHEQUE para checar CHEQUE MATE");
                        }
                        
                        //reverte mudanca - revert change
			Board.setPiece(peca_origem, pos_origem);
			Board.setPiece(peca_destino, pos_destino);
                        try {
                            peca_origem.setPosition(pos_origem);
                        } catch (Exception ex) {
                            GameWindow.getSystemLog().writeInFile("Error when reverting the piece movement to check IF CHECK MATE", "error");
                            System.out.println("Error when reverting the piece movement to check IF CHECK MATE");
//                            GameWindow.getSystemLog().writeInFile("Erro ao reverter movimento de peça para checar CHEQUE MATE", "error");
//                            System.out.println("Erro ao reverter movimento de peça para checar CHEQUE MATE");
                        }
                        
                        if(!check_mate){
                            return check_mate; // = false
                        }
                    }
                }
            }
        }
        return check_mate;// = true
    }
    
    public static void backup(){
        //copia os valores de posicoes para posicoes_backup - copy the position values to the backup positions
        Board.copyDataAcrossBoards(positions, positions_backup);
    }
    public static void undo() {
        GameWindow.getSystemLog().writeInFile("Undoing play...", "info");
//        GameWindow.getSystemLog().writeInFile("Desfazendo jogada...", "info");
        if(no_of_plays>0){
            is_in_undo_state = true;
            
            //copia os valores de posicoes (ATUAL) para aux=> PRESERVA VALORES PARA EVENTUAL REDO
            //copies the values of the positions (CURRENT) to aux => KEEP VALUES FOR AN EVENTUAL "REDO" 
            ArrayList<ArrayList> aux = new ArrayList<ArrayList>();
            Board.initializePositions(aux);
            Board.copyDataAcrossBoards(positions, aux);

            //copia os valores de posicoes_backup para posicoes => COLOCA A JOGADA ANTERIOR COMO ATUAL
            //copies the values from positions_backup to positions => PUT THE PREVIOUS PLAY AS THE CURRENT ONE
            Board.copyDataAcrossBoards(positions_backup, positions);

            //copia os valores de aux para posicoes backup => GUARDA A JOGADA ATUAL PARA TALVEZ SER UTILIZADA NO REDO
            //copies the values from aux to positions_backup => KEEP THE CURRENT PLAY FOR MAYBE BEING REUTILIZED IN CASE OF "REDO"
            Board.copyDataAcrossBoards(aux, positions_backup);

            is_white_turn = !is_white_turn;
            no_of_plays--;
            Main.getFrame().appendToLog("Play undone");
            GameWindow.getSystemLog().writeInFile("Play undone", "info");
//            Main.getFrame().appendToLog("Jogada Desfeita");
//            GameWindow.getSystemLog().writeInFile("Jogada desfeita.", "info");
            resetPlayerClock();
        }else{
            GameWindow.getSystemLog().writeInFile("The play cannot be undone because no play has been done yet.", "warning");
            Main.getFrame().appendToLog("The play cannot be undone because no play has been done yet.");
//            GameWindow.getSystemLog().writeInFile("Jogada não pode ser desfeito quando nenhum movimento foi feito ainda.", "warning");
//            Main.getFrame().appendToLog("Jogada não pode ser desfeito quando nenhum movimento foi feito ainda.");
        }
    }

    public static void redo() {
        if(is_in_undo_state){
            //copia os valores de posicoes_backup para posicoes - copy values from positions_backup to positions
            Board.copyDataAcrossBoards(positions_backup, positions);
            is_white_turn = !is_white_turn;
            no_of_plays++;
            Main.getFrame().appendToLog("Play redone");
            GameWindow.getSystemLog().writeInFile("Play redone.", "info");
//            Main.getFrame().appendToLog("Jogada Refeita");
//            GameWindow.getSystemLog().writeInFile("Jogada refeita.", "info");
            is_in_undo_state = false;
            resetPlayerClock();
        }else{
            Main.getFrame().appendToLog("Play cannot be redone because it has not been undone.");
            GameWindow.getSystemLog().writeInFile("Play cannot be redone because it has not been undone.", "warning");
//            Main.getFrame().appendToLog("Jogada não pode ser refeita quando ela não é desfeita primeiro.");
//            GameWindow.getSystemLog().writeInFile("Jogada não pode ser refeita quando ela não é desfeita primeiro.", "warning");
            //throw exception 
        }
    }
    private static void copyDataAcrossBoards(ArrayList<ArrayList> board_from, ArrayList<ArrayList> board_to){
        
        for(int i=1;i<=8;i++){
            for(int j=1;j<=8;j++){
                Piece p_aux = (Piece) board_from.get(i).get(j);
                if(p_aux == null){
                    board_to.get(i).set(j, null);
                }else{
                     if(p_aux.getClass().equals(Pawn.class)){
                        board_to.get(i).set(j, new Pawn(p_aux));
                    }
                    if(p_aux.getClass().equals(Tower.class)){
                        board_to.get(i).set(j, new Tower(p_aux));
                    }
                    if(p_aux.getClass().equals(Horse.class)){
                        board_to.get(i).set(j, new Horse(p_aux));
                    }
                    if(p_aux.getClass().equals(Bishop.class)){
                       board_to.get(i).set(j, new Bishop(p_aux));
                    }
                    if(p_aux.getClass().equals(Queen.class)){
                       board_to.get(i).set(j, new Queen(p_aux));
                    }
                    if(p_aux.getClass().equals(King.class)){
                        board_to.get(i).set(j, new King(p_aux));
                    }else{
                        //THROW EXCEPTION
                    }
                }
            }
        }
        //copia informações
        
    }

    static void autosave() {
        GameWindow.getSystemLog().writeInFile("iniciando Autosave...", "info");
        System.out.println("Autosave in progress...");
        String teste_loc = "./autosave.txt";
        save(teste_loc);

//        String teste = FileManager.encodeGameToJson();
//        String teste_loc = "./autosave.txt";
//        if(FileManager.writeInFile(teste, teste_loc)){
//            System.out.println("Autosave SUCCESS");
//        }else{
//            System.out.println("Autosave NOT SUCCESS");
//        }
//        
//        System.out.println("LE DE ARQUIVO...");
//        String file_content = new String();
//        try {
//            file_content = FileManager.readFromFile(teste_loc);
//        } catch (IOException ex) {
//            System.out.println("exception LE DE ARQUIVO...");
////            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        FileManager.decodeGameFromJson(file_content);
//        System.out.println("json decoded...");
        
    }

    static void incrementMainClock() {
        game_time++;
        Main.getFrame().refreshMainClock();
    }

    static void resetPlayerClock() {
        player_time = 0;
        Main.getFrame().refreshPlayerClock();
    }
    static void incrementPlayerClock() {
        player_time++;
        Main.getFrame().refreshPlayerClock();
    }
    
    public static String formatClockTime(int time_milliseconds){
        if(time_milliseconds<0){
            //throw exception
        }
        int minutes = (int) Math.floor(time_milliseconds/60000);
        int seconds = ((int) Math.floor(time_milliseconds/1000))%60;
        int milli = time_milliseconds%1000;
        return String.format("%02d:%02d.%03d", minutes, seconds,milli);
    }
    public static String getGameTime() {
        return formatClockTime(game_time);
    }

    public static String getPlayerTime() {
        return formatClockTime(player_time);
    }

    static ArrayList<ArrayList> getCurrentBoard() {
        return positions;
    }

    static void loadAndMapPositions(ArrayList<Piece> temp_piece) throws Exception {
//        initializeBoard();
        Board.initializePositions();
        
        for(Piece p: temp_piece){
            setPiece(p, p.getPosition());
        }
    }

    public static void save(String location_to_save) {
        GameWindow.getSystemLog().writeInFile("Saving game in "+location_to_save+"...", "info");
//        GameWindow.getSystemLog().writeInFile("Salvando jogo em "+location_to_save+"...", "info");
        String teste = FileManager.encodeGameToJson();
        if(FileManager.writeInFile(teste, location_to_save)){
            System.out.println("Autosave SUCCESSFULLY DONE");
            GameWindow.getSystemLog().writeInFile("Autosave successfully done. Current game saved in "+location_to_save+"...", "info");
//            GameWindow.getSystemLog().writeInFile("Autosave com sucesso. Jogo salvo em "+location_to_save+"...", "info");
        }else{
            GameWindow.getSystemLog().writeInFile("No success on autosave. Game could not be save in "+location_to_save+"...", "warning");
//            GameWindow.getSystemLog().writeInFile("Autosave SEM sucesso. Jogo não pode ser salvo em "+location_to_save+"...", "warning");
            System.out.println("Autosave NOT SUCCESS");
        }
    }
    public static void load(String file_location) {
        GameWindow.getSystemLog().writeInFile("Loading game from "+file_location+"...", "info");
        System.out.println("READING FROM FILE...");
//        GameWindow.getSystemLog().writeInFile("Carregando jogo de "+file_location+"...", "info");
//        System.out.println("LE DE ARQUIVO...");
        String file_content = new String();
        try {
            file_content = FileManager.readFromFile(file_location);
        } catch (IOException ex) {
            GameWindow.getSystemLog().writeInFile("Error when reading game from "+file_location+". Throwing Exception...", "error");
            System.out.println("Exception when reading file...");
//            GameWindow.getSystemLog().writeInFile("Erro ao ler o arquivo "+file_location+". Lançando Exception...", "error");
//            System.out.println("exception LE DE ARQUIVO...");
        }
        try {
            //carrega o array de Pecas para as posicoes do tabuleiro - Load the array of pieces to the current board's positions
            loadAndMapPositions(FileManager.decodeGameFromJson(file_content));
        } catch (Exception ex) {
            GameWindow.getSystemLog().writeInFile("Error when loading the game from "+file_location+". Throwing Exception...", "error");
            System.out.println("exception when loading file...");
//            GameWindow.getSystemLog().writeInFile("Erro ao carregar jogo de "+file_location+". Lançando Exception...", "error");
//            System.out.println("exception CARREGAR DE ARQUIVO...");
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
