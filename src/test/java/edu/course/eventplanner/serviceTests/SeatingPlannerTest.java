package edu.course.eventplanner.serviceTests;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.SeatingPlanner;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SeatingPlannerTest {
    @Test
    void testNotEnoughSeats() {
        Venue sands = new Venue("Sands Atlantic", 100000, 1, 50, 10);
        SeatingPlanner sp = new SeatingPlanner(sands);
        List<Guest> guests= new LinkedList<>();
        Guest harvey = new Guest("Harvey", "neighbor");
        Guest deidre = new Guest("Deidre", "family");
        guests.add(harvey);
        guests.add(deidre);
        sp.generateSeating(guests);
        assertNull(sp.generateSeating(guests));
    }

    @Test
    void testGenerateSeatingWithEnoughTables() {
        Venue sands = new Venue("Sands Atlantic", 100000, 30, 3, 10);
        SeatingPlanner sp = new SeatingPlanner(sands);
        List<Guest> guests= new LinkedList<>();
        Guest harvey = new Guest("Harvey", "neighbor");
        Guest deidre = new Guest("Deidre", "family");
        Guest harveyy = new Guest("Harvey", "neighbor");
        Guest deidree = new Guest("Deidre", "family");
        Guest harveyyy = new Guest("Harvey", "neighbor");
        Guest deidreee = new Guest("Deidre", "family");
        guests.add(harvey);
        guests.add(deidre);
        guests.add(harveyy);
        guests.add(deidree);
        guests.add(harveyyy);
        guests.add(deidreee);
        Map<Integer, List<Guest>> answer= sp.generateSeating(guests);
        assertEquals(3, answer.get(1).size());
        assertEquals(3, answer.get(2).size());
        assertEquals(2, answer.size());
    }
    @Test
    void testGenerateSeatingWithNotEnoughTables() {
        Venue sands = new Venue("Sands Atlantic", 100000, 6, 2, 3);
        SeatingPlanner sp = new SeatingPlanner(sands);
        List<Guest> guests= new LinkedList<>();
        Guest harvey = new Guest("Harvey", "neighbor");
        Guest deidre = new Guest("Deidre", "family");
        Guest harveyy = new Guest("Harvey", "neighbor");
        Guest deidree = new Guest("Deidre", "family");
        Guest harveyyy = new Guest("Harvey", "neighbor");
        Guest carmen = new Guest("Carmen", "friend");
        guests.add(harvey);
        guests.add(deidre);
        guests.add(harveyy);
        guests.add(deidree);
        guests.add(harveyyy);
        guests.add(carmen);
        Map<Integer, List<Guest>> answer= sp.generateSeating(guests);
        assertEquals(2, answer.size());
        List<Integer> tableSizes = answer.values()
                .stream()
                .map(List::size)
                .sorted()
                .toList();
        assertEquals(List.of(3, 3), tableSizes);
    }
    @Test
    void testGenerateSeatingWithSplitGroupTables() {
        Venue sands = new Venue("Sands Atlantic", 100000, 6, 2, 3);
        SeatingPlanner sp = new SeatingPlanner(sands);
        List<Guest> guests= new LinkedList<>();
        Guest harvey = new Guest("Harvey", "neighbor");
        Guest deidre = new Guest("Deidre", "family");
        Guest harveyy = new Guest("Harvey", "neighbor");
        Guest deidree = new Guest("Deidre", "family");
        Guest harveyyy = new Guest("Harvey", "neighbor");
        Guest carmen = new Guest("Carmen", "neighbor");
        guests.add(harvey);
        guests.add(deidre);
        guests.add(harveyy);
        guests.add(deidree);
        guests.add(harveyyy);
        guests.add(carmen);
        Map<Integer, List<Guest>> answer= sp.generateSeating(guests);
        assertEquals(2, answer.size());
        boolean neighborOnFamilyTable = false;
        for (List<Guest> table : answer.values()) {
            boolean hasFamily = table.stream()
                    .anyMatch(g -> g.getGroupTag().equals("family"));

            boolean hasNeighbor = table.stream()
                    .anyMatch(g -> g.getGroupTag().equals("neighbor"));

            if (hasFamily && hasNeighbor) {
                neighborOnFamilyTable = true;
                break;
            }
        }
        assertTrue(neighborOnFamilyTable);
    }
}
