/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.dependencies;

import erserver.modules.dependencies.vendorpagersystem.PagerSystem;
import erserver.modules.dependencies.vendorpagersystem.PagerTransport;

/**
 *
 * @author Alexander
 */
public class PagerSystemAlertTransmitter implements AlertTransmitter {


    public PagerSystemAlertTransmitter() {
    }

    private PagerTransport getInitializedTransportSystem() {
        PagerTransport transport = PagerSystem.getTransport();
        transport.initialize();
        return transport;
    }


    @Override
    public void transmit(String targetDevice, String pageText) {
        PagerTransport transport = getInitializedTransportSystem();
        transport.transmit(targetDevice, pageText);
    }

    @Override
    public void transmitRequiringAcknowledgement(String targetDevice, String pageText) {
        PagerTransport transport = getInitializedTransportSystem();
        transport.transmitRequiringAcknowledgement(targetDevice, pageText);
    }

}
