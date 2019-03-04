/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.hardunderstand;

/**
 *
 * @author Alexander
 */
public class DivergenceReportBuilder {
    
    int redInboundCount;
    int yellowInboundCount;
    int greenInboundCount;
    int[] availableStaff;
    int[] neededStaff;
    int bedsAvailable;
    int criticalBedsAvailable;

    public DivergenceReportBuilder(int redInboundCount, int yellowInboundCount, int greenInboundCount, int[] availableStaff, int[] neededStaff, int bedsAvailable, int criticalBedsAvailable) {
        this.redInboundCount = redInboundCount;
        this.yellowInboundCount = yellowInboundCount;
        this.greenInboundCount = greenInboundCount;
        this.availableStaff = availableStaff;
        this.neededStaff = neededStaff;
        this.bedsAvailable = bedsAvailable;
        this.criticalBedsAvailable = criticalBedsAvailable;
    }
    

    

    public String buildReport() {
        return "\nSituation Status: \n" + "Inbound patients requiring beds: "
                + redInboundCount + " Red, " + yellowInboundCount + " Yellow, " + greenInboundCount + " Green.\n"
                + "Available Docs/Nurses: " + availableStaff[0] + "/" + availableStaff[1] + "\n"
                + "Needed Docs/Nurses: " + neededStaff[0] + "/" + neededStaff[1] + "\n"
                + "Available Total Beds/Crit Beds: " + bedsAvailable + "/" + criticalBedsAvailable;
    }
    
    
    
}
