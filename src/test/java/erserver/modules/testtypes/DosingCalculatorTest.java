package erserver.modules.testtypes;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import org.junit.After;

import static org.junit.Assert.*;

public class DosingCalculatorTest {

    private DosingCalculator dosingCalculator;
    private PatientForTesting patient;

    @Before
    public void setUp() {
        dosingCalculator = new DosingCalculator();
        patient = new PatientForTesting();
    }

    @After
    public void tearDown() {
        dosingCalculator = null;
        patient = null;
    }

    @Test
    public void returnsCorrectDosesForNeonate() {
        patient.setCurrentDate(LocalDate.of(2018, Month.MARCH, 30));
        patient.setBirthDate(LocalDate.of(2018, Month.MARCH, 1));

        String singleDose = dosingCalculator.getRecommendedSingleDose(patient, "Tylenol Oral Suspension");
        assertEquals("0", singleDose);
    }

    @Test
    public void returnsCorrectDosesForInfant() {
        patient.setCurrentDate(LocalDate.of(2018, Month.APRIL, 30));
        patient.setBirthDate(LocalDate.of(2018, Month.MARCH, 1));
        String singleDose = dosingCalculator.getRecommendedSingleDose(patient, "Tylenol Oral Suspension");
        assertEquals("2.5 ml", singleDose);
    }

    @Test
    public void returnsCorrectDosesForChild() {
        patient.setCurrentDate(LocalDate.of(2018, Month.APRIL, 30));
        patient.setBirthDate(LocalDate.of(2015, Month.MARCH, 1));
        String singleDose = dosingCalculator.getRecommendedSingleDose(patient, "Tylenol Oral Suspension");
        assertEquals("5 ml", singleDose);
    }

    @Test
    public void returnsCorrectDosesForNeonateAmox() {
        patient.setCurrentDate(LocalDate.of(2018, Month.MARCH, 20));
        patient.setBirthDate(LocalDate.of(2018, Month.MARCH, 1));
        String singleDose = dosingCalculator.getRecommendedSingleDose(patient, "Amoxicillin Oral Suspension");
        assertEquals("15 mg/kg", singleDose);
    }

    @Test(expected = RuntimeException.class)
    public void returnsExceptionForAdults() {
        patient.setCurrentDate(LocalDate.of(2018, Month.MARCH, 20));
        patient.setBirthDate(LocalDate.of(2002, Month.MARCH, 1));
        dosingCalculator.getRecommendedSingleDose(patient, "Amoxicillin Oral Suspension");
    }

    @Test(expected = RuntimeException.class)
    public void returnsNullForUnrecognizedMedication() {
        patient.setCurrentDate(LocalDate.of(2018, Month.MARCH, 20));
        patient.setBirthDate(LocalDate.of(2002, Month.MARCH, 1));
        dosingCalculator.getRecommendedSingleDose(patient, "No Such Med");
    }
}
