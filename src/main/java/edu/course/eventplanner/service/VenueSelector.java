package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Venue;
import java.util.*;

public class VenueSelector {
    private final List<Venue> venues;
    public VenueSelector(List<Venue> venues) { this.venues = venues; }
    public Venue selectVenue(double budget, int guestCount) {
        double currentCost = 999999999;
        Venue chosen = new Venue("Unknown", 0, 0, 0, 0);
        for (Venue venue:venues) {
            if (venue.getCapacity() > guestCount) {
                if (venue.getCost() < budget) {
                    if (venue.getCost() < currentCost) {
                        currentCost = venue.getCost();
                        chosen = venue;
                    }
                }
            }
        }
        if ((chosen.getName().equals("Unknown"))) {
            return null;
        }
        return chosen;
    }
}
