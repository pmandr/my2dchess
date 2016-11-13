/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xadrez.controller;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.IOUtils;
import xadrez.model.Piece;
import xadrez.model.PieceJSON;
import xadrez.view.GameWindow;

public class FileManager {

    public static boolean writeInFile(String content, String file_loc) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file_loc));
            writer.write(content);

        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                    return true;
                }
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public static String readFromFile(String file_loc) throws IOException {
        InputStream inputstream;
        try {
            inputstream = new FileInputStream(file_loc);
        } catch (FileNotFoundException ex) {
             GameWindow.getSystemLog().writeInFile("File not found in "+file_loc, "error");
            System.out.println("FILE NOT FOUND IN " + file_loc);
            return null;
        }
        StringBuilder sb = new StringBuilder(Math.max(16, inputstream.available()));
        char[] tmp = new char[8000];

        try {
            InputStreamReader reader = new InputStreamReader(inputstream, "UTF-8");
            for (int cnt; (cnt = reader.read(tmp)) > 0;) {
                sb.append(tmp, 0, cnt);
            }
        } finally {
            inputstream.close();
        }
        return sb.toString();
    }

    public static ArrayList<Piece> decodeGameFromJson(String json_str) {
        ArrayList<PieceJSON> saved_game = new ArrayList<PieceJSON>();
        ArrayList<LinkedTreeMap> temp_json;
        ArrayList<Piece> saved_game_return = new ArrayList<Piece>();
        
        Gson gson = new Gson();
        
        //transforma string em arraylist de LinkedTreeMap
        //Transform string in arraylist of LinkedTreeMap
        temp_json = gson.fromJson(json_str, ArrayList.class);
        
        //CONVERTE os LinkedTreeMap's para PieceJSON (Piece nao eh usada pq eh uma classe abstrata e nao pode ser instanciada
        //Converts the LinkedTreeMap's to PieceJSON (Piece is not used because it's an abstract class, thus cannot be instatiated
//        for(int i=0; i<temp_json.size(); i++){
        for(LinkedTreeMap peca: temp_json){
            saved_game.add(gson.fromJson(peca.toString(), PieceJSON.class));
//            jogo_salvo.add(gson.fromJson(temp_json.get(i).toString(), PieceJSON.class));
        }
        
        //converte os objetos do tipo PieceJSON para serem do tipo Piece
        for(PieceJSON piece :saved_game){
            saved_game_return.add(Piece.pieceFactory(piece));
        }
        
        return saved_game_return;
    }

    public static String encodeGameToJson() {
        System.out.println("JSON encoding...");
        Gson gson = new Gson();
        String json = gson.toJson(preparePiecesToJsonEnconde());

        return json;
    }
    public static ArrayList<PieceJSON> preparePiecesToJsonEnconde(){
        ArrayList<PieceJSON> pecas = new ArrayList<PieceJSON>();
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                Piece temp = Board.getPiece(i, j);
                if(temp != null){
                    pecas.add(new PieceJSON().importaPeca(temp));
                }
            }
        }
        return pecas;
    }
}
