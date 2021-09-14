/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock;

import entities.Alarm;
import entities.Pesma;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author pc
 */

public class PeriodicAlarm extends Thread {
    
    private int idA;
    
    public PeriodicAlarm(int idA) {
        this.idA = idA;
    }
    
    @Override
    public void run() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AlarmClockPU");
        EntityManager em = emf.createEntityManager();
        // Alarm alarm = em.find(Alarm.class, idA);
        // Pesma song = em.find(Pesma.class, alarm.getIdP().getIdP());
        
        while(true) {
            try {                
                Alarm alarm = em.find(Alarm.class, idA);                
                Pesma song = em.find(Pesma.class, alarm.getIdP().getIdP());
                Thread.sleep(alarm.getPerioda() * 1000);
                if (Desktop.isDesktopSupported()) {
                    if (song != null) {
                        Desktop.getDesktop().browse(new URI(song.getUrl()));
                    }
                }   
            } catch (InterruptedException | IOException | URISyntaxException ex) {
                Logger.getLogger(PeriodicAlarm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}