package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Venue;
import java.util.*;

public class VenueSelector {
    private final List<Venue> venues;
    TreeMap<Double, Venue> tm = new TreeMap<>();

    public VenueSelector(List<Venue> venues) { this.venues = venues;
       for (Venue venue: venues) {
           tm.put(venue.getCost(), venue);
       }


    }
    public Venue selectVenue(double budget, int guestCount) {
        for (Map.Entry<Double, Venue> entry : tm.entrySet()) {
            Venue venue = entry.getValue();
            if (entry.getKey() <= budget &&
                    venue.getCapacity() >= guestCount) {
                return venue;
            }
        }
        return null;
    }
}
