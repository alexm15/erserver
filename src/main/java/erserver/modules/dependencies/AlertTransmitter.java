/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.dependencies;

/**
 *
 * @author Alexander
 */
public interface AlertTransmitter {

    void transmit(String targetDevice, String pageText);

    void transmitRequiringAcknowledgement(String targetDevice, String pageText);
    
}
