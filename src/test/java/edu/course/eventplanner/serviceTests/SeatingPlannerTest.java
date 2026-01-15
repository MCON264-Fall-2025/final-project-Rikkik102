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

    @Test
    void testGenerateSeatingWithOverflowToEmptyTables() {
        Venue venue = new Venue("Sands", 100000, 20, 5, 4);
        SeatingPlanner sp = new SeatingPlanner(venue);
        List<Guest> guests = new LinkedList<>();

        // Create a group larger than seatsPerTable
        for (int i = 0; i < 7; i++) {
            guests.add(new Guest("Person" + i, "cousins"));
        }

        guests.add(new Guest("Harvey", "friend"));
        guests.add(new Guest("Deidre", "friend"));

        Map<Integer, List<Guest>> answer = sp.generateSeating(guests);

        // Should have at least 2 tables
        assertTrue(answer.size() >= 2);

        int totalSeated = answer.values().stream()
                .mapToInt(List::size)
                .sum();
        assertEquals(9, totalSeated);
    }

    @Test
    void testGenerateSeatingWithOverflowToTablesWithExtraRoom() {
        // Tests the scenario where overflow guests fill tables with extra room
        Venue venue = new Venue("Sands Atlantic", 100000, 12, 3, 3); // 3 tables, 3 seats each
        SeatingPlanner sp = new SeatingPlanner(venue);
        List<Guest> guests = new LinkedList<>();

        guests.add(new Guest("Harvey", "group1"));
        guests.add(new Guest("Harveyy", "group1"));

        guests.add(new Guest("Dierdre", "group2"));
        guests.add(new Guest("Dierdree", "group2"));

        for (int i = 0; i < 5; i++) {
            guests.add(new Guest("Guy " + i, "group3"));
        }

        Map<Integer, List<Guest>> answer = sp.generateSeating(guests);

        // Should use 3 tables
        assertEquals(3, answer.size());

        int totalSeated = answer.values().stream()
                .mapToInt(List::size)
                .sum();
        assertEquals(9, totalSeated);

        boolean hasMixedTable = false;
        for (List<Guest> table : answer.values()) {
            long uniqueGroups = table.stream()
                    .map(Guest::getGroupTag)
                    .distinct()
                    .count();
            if (uniqueGroups > 1) {
                hasMixedTable = true;
                break;
            }
        }
        assertTrue(hasMixedTable, "One table should have more than one group on it");
    }


    @Test
    void testGenerateSeatingWithUnplacedGroupsSplitting() {
        // Tests the unplaced groups logic where groups must be split
        Venue venue = new Venue("Valhalla", 100000, 5, 2, 2);
        SeatingPlanner sp = new SeatingPlanner(venue);
        List<Guest> guests = new LinkedList<>();

        // Create one large group that won't fit on a single table
        for (int i = 0; i < 5; i++) {
            guests.add(new Guest("Guy " + i, "fam"));
        }

        Map<Integer, List<Guest>> answer = sp.generateSeating(guests);

        // Should use both tables
        assertEquals(2, answer.size());

        int totalSeated = answer.values().stream()
                .mapToInt(List::size)
                .sum();
        assertTrue(totalSeated >= 4 && totalSeated <= 5);
    }
}
