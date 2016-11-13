
package xadrez.controller;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.JPanel;
/**
 * 
 * @author Paulo
 * a classe Timer controla estas operações agendadas: autosave, relogio principal
 * a classe Timer controla estas operações não agendadas: tempo de jogada do jogador atual
 * 
 * The class Timer controls these scheduled tasks: Autosave, Main Watch
 * The class Timer controls these NOT scheduled tasks: 
 *      - time of the turn(play) of the current playe
 *      - Refresh of the game panel
 *      - Others
 */

public class Timer{
    //private MP3Player mp3pl = new MP3Player();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
    
    private final Runnable timerAutosave;
    private final Runnable timerOneSecond;
    
    private final ScheduledFuture<?> AutoSaveHandle;
    private final ScheduledFuture<?> MainWatchHandler;
            
    /**
     *@author Paulo
     */
    public Timer(){
        //Defines autosave task
        this.timerAutosave = new Runnable() {
                @Override
                public void run() { 
                    Board.autosave(); 
                }
            };
        
        //DEFINE FREQUENCIA DA EXECUCAO DA TAREFA ACIMA E A LIGA COM O SCHEDULER
        //Define frequency of the execution of the task above and links it with the scheduler
        this.AutoSaveHandle = scheduler.scheduleAtFixedRate(timerAutosave, 5, 10, TimeUnit.SECONDS);    
        
        
        //DEFINE TAREFA DE incrementar o relogio principal
        //Defines the task for incremention the main clock
        this.timerOneSecond = new Runnable() {
                @Override
                public void run() { 
                    Board.incrementMainClock(); 
                    Board.incrementPlayerClock(); 
                }
            };
        
        //DEFINE FREQUENCIA DA EXECUCAO DA TAREFA ACIMA E A LIGA COM O SCHEDULER
        //Define frequency of the execution of the task above and links it with the scheduler
        this.MainWatchHandler = scheduler.scheduleAtFixedRate(timerOneSecond, 1000, 1, TimeUnit.MILLISECONDS); 
//        this.RelogioPrincipalHandle = scheduler.scheduleAtFixedRate(timerUmSegundo, 1, 1, TimeUnit.SECONDS); 
        
    }
    
	 
}
 
