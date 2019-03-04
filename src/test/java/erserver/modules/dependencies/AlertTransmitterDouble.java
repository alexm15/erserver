/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.dependencies;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander
 */
public class AlertTransmitterDouble implements AlertTransmitter {

 
    private List<String> alertRevieced;
    private List<String> alertReviecedAck;

    public AlertTransmitterDouble() {
        this.alertRevieced = new ArrayList<>();
        this.alertReviecedAck = new ArrayList<>();
    }
    
    

    @Override
    public void transmit(String targetDevice, String pageText) {
        alertRevieced.add(targetDevice + " : " + pageText);
    }

    @Override
    public void transmitRequiringAcknowledgement(String targetDevice, String pageText) {
        alertReviecedAck.add(targetDevice + " : " + pageText);
    }

    public List<String> getAlertRevieced() {
        return alertRevieced;
    }

    public List<String> getAlertReviecedAck() {
        return alertReviecedAck;
    }
    
    


    
    
    
    
    
}
