package edu.course.eventplanner.service;

import edu.course.eventplanner.model.*;
import java.util.*;

public class SeatingPlanner {
    private final Venue venue;
    HashMap<String, List<Guest>> hm = new HashMap<>();
    HashMap<Integer, List<Guest>> answer = new HashMap<>();
    public SeatingPlanner(Venue venue) { this.venue = venue; }
    public Map<Integer, List<Guest>> generateSeating(List<Guest> guests) {
        if (venue.getCapacity() < guests.size()) {
            return null;
        }
        else {
            for (Guest guest: guests) {
                if (hm.containsKey(guest.getGroupTag())) {
                    hm.get(guest.getGroupTag()).add(guest);
                }
                else {
                    List<Guest> list = new LinkedList<>();
                    list.add(guest);
                    hm.put(guest.getGroupTag(), list);
                }
            }

            int numOfGroups = hm.size();

            int seats = venue.getSeatsPerTable();

            int maxTables = venue.getTables();

            List<Guest> clique;
            List<Guest> overflow;
            List<Guest> tables;

            if (maxTables >= numOfGroups) {
                Integer currTable = 1;
                for (Map.Entry<String, List<Guest>> entry : hm.entrySet()) {
                    String key = entry.getKey();
                    clique = hm.get(key);
                    answer.put(currTable, clique);
                    currTable++;
                }

            for (Map.Entry<String, List<Guest>> entry : hm.entrySet()) {

                String key = entry.getKey();
                clique = hm.get(key);


                }
            }

        }

        return Map.of();
    }
}
