package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.util.Generators;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratorsTest {
    @Test
    void testGenerateVenuesReturnsExpectedVenues() {
        List<Venue> venues = Generators.generateVenues();

        assertEquals(3, venues.size());

        Venue v1 = venues.get(0);
        assertEquals("Community Hall", v1.getName());
        assertEquals(1500, v1.getCost());
        assertEquals(40, v1.getCapacity());
        assertEquals(5, v1.getTables());
        assertEquals(8, v1.getSeatsPerTable());

        Venue v2 = venues.get(1);
        assertEquals("Garden Hall", v2.getName());
        assertEquals(2500, v2.getCost());
        assertEquals(60, v2.getCapacity());

        Venue v3 = venues.get(2);
        assertEquals("Grand Ballroom", v3.getName());
        assertEquals(5000, v3.getCost());
        assertEquals(120, v3.getCapacity());
    }

    @Test
    void testGenerateGuestsCreatesCorrectNumberOfGuests() {
        int n = 10;
        List<Guest> guests = Generators.GenerateGuests(n);
        assertEquals(n, guests.size());
    }

    @Test
    void testGenerateGuestsNamesAreInOrder() {
        List<Guest> guests = Generators.GenerateGuests(5);

        assertEquals("Guest1", guests.get(0).getName());
        assertEquals("Guest2", guests.get(1).getName());
        assertEquals("Guest5", guests.get(4).getName());
    }

    @Test
    void testGenerateGuestsGroupAssignmentCyclesCorrectly() {
        List<Guest> guests = Generators.GenerateGuests(8);

        assertEquals("friends", guests.get(0).getGroupTag());   // 1 % 4
        assertEquals("neighbors", guests.get(1).getGroupTag());
        assertEquals("coworkers", guests.get(2).getGroupTag());
        assertEquals("family", guests.get(3).getGroupTag());
        assertEquals("friends", guests.get(4).getGroupTag());
    }

    @Test
    void testGenerateGuestsWithZeroReturnsEmptyList() {
        List<Guest> guests = Generators.GenerateGuests(0);
        assertNotNull(guests);
        assertTrue(guests.isEmpty());
    }
}
