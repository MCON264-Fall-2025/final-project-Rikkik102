package edu.course.eventplanner.service;

import edu.course.eventplanner.model.*;
import java.util.*;

public class SeatingPlanner {
    private final Venue venue;
    HashMap<String, List<Guest>> hm = new HashMap<>();
    HashMap<Integer, List<Guest>> answer = new HashMap<>();

    public SeatingPlanner(Venue venue) {
        this.venue = venue;
    }

    public Map<Integer, List<Guest>> generateSeating(List<Guest> guests) {
        if (venue.getCapacity() < guests.size()) {
            return null;
        } else {
            for (Guest guest : guests) {
                if (hm.containsKey(guest.getGroupTag())) {
                    hm.get(guest.getGroupTag()).add(guest);
                } else {
                    List<Guest> list = new LinkedList<>();
                    list.add(guest);
                    hm.put(guest.getGroupTag(), list);
                }
            }

            int numOfGroups = hm.size();
            int seatsPerTable = venue.getSeatsPerTable();
            int maxTables = venue.getTables();

            List<Guest> clique;
            List<Guest> overflow = new ArrayList<>();
            List<Integer> tablesWithExtraRoom = new ArrayList<>();

            if (maxTables >= numOfGroups) {
                Integer currTable = 1;

                for (Map.Entry<String, List<Guest>> entry : hm.entrySet()) {
                    clique = entry.getValue();

                    while (clique.size() > seatsPerTable) {
                        overflow.add(clique.removeLast());
                    }

                    answer.put(currTable, clique);

                    if (clique.size() < seatsPerTable) {
                        tablesWithExtraRoom.add(currTable);
                    }

                    currTable++;
                }

                if (!overflow.isEmpty() && maxTables > numOfGroups) {
                    //put extra guests on empty tables
                    for (Integer i = currTable; i <= maxTables && !overflow.isEmpty(); i++) {
                        List<Guest> extraGuests = new ArrayList<>();

                        while (!overflow.isEmpty() && extraGuests.size() < seatsPerTable) {
                            extraGuests.add(overflow.removeFirst());
                        }

                        answer.put(i, extraGuests);
                    }
                }

                if (!overflow.isEmpty()) {
                    // put guests on table with extra room
                    for (Integer key : tablesWithExtraRoom) {
                        List<Guest> table = answer.get(key);

                        while (!overflow.isEmpty() && table.size() < seatsPerTable) {
                            table.add(overflow.removeFirst());
                        }

                        if (overflow.isEmpty()) break;
                    }
                }
            }

            return answer;
        }
    }
}
