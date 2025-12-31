package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import java.util.*;

public class GuestListManager {
    private final LinkedList<Guest> guests = new LinkedList<>();
    private final Map<String, Guest> guestByName = new HashMap<>();

    public void addGuest(Guest guest) {
       guests.add(guest);
        guestByName.put(guest.getName(), guest);
    }

    public boolean removeGuest(String guestName) {
        for (Guest guest: guests) {
            if (guest.getName().equals(guestName)) {
                guests.remove(guest);
                guestByName.remove(guestName, guest);
                return true;
            }
        }
        return false;
    }

    public Guest findGuest(String guestName) {
        return guestByName.get(guestName);
    }
    public int getGuestCount() {
        return guests.size(); }

    public List<Guest> getAllGuests() {
        return guests;
    }

    public Map<String, Guest> getGuestMap() {
        return guestByName;
    }
}
