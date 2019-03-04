/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erserver.modules.testtypes;

import java.time.LocalDate;

/**
 *
 * @author Alexander
 */
public class PatientForTesting extends Patient {

    private LocalDate currentDate;

    public PatientForTesting() {
    }

    public void setCurrentDate(LocalDate date) {
        currentDate = date;
    }
    
    @Override
    protected LocalDate getCurrentDate() {
        return currentDate;
    }
    
    
}
