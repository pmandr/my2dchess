package xadrez.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class SystemLog {

    private static final String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
    private static SystemLog instance = null;
    private static String filename = "./system_log.txt";

    protected SystemLog() {
        // Exists only to defeat instantiation.
    }

    public static SystemLog getInstance() {
        if (instance == null) {
            instance = new SystemLog();
        }
        return instance;
    }

    public boolean writeInFile(String conteudo, String tipo_msg) {
        if (tipo_msg.compareToIgnoreCase("error") == 0) {
            conteudo = "[ERROR]"+conteudo;
        } else if (tipo_msg.compareToIgnoreCase("warning") == 0) {
            conteudo = "[WARNING]"+conteudo;
        } else if (tipo_msg.compareToIgnoreCase("debug") == 0) {
            conteudo = "[DEBUG]"+conteudo;
        } else if (tipo_msg.compareToIgnoreCase("info") == 0) {
            conteudo = "[INFO]"+conteudo;
        }
        conteudo = "[" + now() + "]" + conteudo +"\n";
        System.out.println("[SysLog]"+conteudo);
       
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename,true));
            writer.write(conteudo);

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

    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

}
