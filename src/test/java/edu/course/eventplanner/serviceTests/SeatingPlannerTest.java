package edu.course.eventplanner.serviceTests;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.SeatingPlanner;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class SeatingPlannerTest {
    @Test
    void testSmallHallGenerateSeating() {
        Venue sands = new Venue("Sands Atlantic", 100000, 1, 50, 10);
        SeatingPlanner sp = new SeatingPlanner(sands);
        List<Guest> guests= new LinkedList<>();
        Guest harvey = new Guest("Harvey", "neighbor");
        Guest deidre = new Guest("Deidre", "family");
        guests.add(harvey);
        guests.add(deidre);
        sp.generateSeating(guests);
    }
}
