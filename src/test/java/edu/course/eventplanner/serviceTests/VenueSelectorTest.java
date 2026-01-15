package edu.course.eventplanner.serviceTests;

import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.VenueSelector;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class VenueSelectorTest {
    @Test
    void testSelectVenue() {
        Venue thePalace = new Venue("Palace", 100000, 500, 50, 10);
        Venue Marina = new Venue("Marina", 200000, 700, 70, 10);
        Venue Sands = new Venue("Sands", 10000, 400, 40, 10);
        Venue CountryClub = new Venue("CountryClub", 10000, 500, 50, 10);
        List<Venue> venues = new LinkedList<>();
        venues.add(thePalace);
        venues.add(Marina);
        venues.add(Sands);
        venues.add(CountryClub);
        VenueSelector vs = new VenueSelector(venues);
        assertEquals(CountryClub, vs.selectVenue(50000, 450));
    }

    @Test
    void testSelectNoVenue() {
        Venue thePalace = new Venue("Palace", 100000, 500, 50, 10);
        Venue Marina = new Venue("Marina", 200000, 700, 70, 10);
        Venue Sands = new Venue("Sands", 10000, 400, 40, 10);
        Venue CountryClub = new Venue("CountryClub", 10000, 500, 50, 10);
        List<Venue> venues = new LinkedList<>();
        venues.add(thePalace);
        venues.add(Marina);
        venues.add(Sands);
        venues.add(CountryClub);
        VenueSelector vs = new VenueSelector(venues);
        assertNull(vs.selectVenue(50000, 850));
    }
}
