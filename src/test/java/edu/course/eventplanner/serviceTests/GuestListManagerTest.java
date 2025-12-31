package edu.course.eventplanner.serviceTests;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuestListManagerTest {

    @Test
    void testsAddGuest() {
        Guest harvey = new Guest("Harvey", "Neighbor");
        GuestListManager glm = new GuestListManager();
        glm.addGuest(harvey);
        assertEquals(1, glm.getAllGuests().size());
        assertEquals(1, glm.getGuestMap().size());
    }
    @Test
    void testRemoveGuest() {
        Guest harvey = new Guest("Harvey", "Neighbor");
        GuestListManager glm = new GuestListManager();
        glm.addGuest(harvey);
        assert(glm.removeGuest("Harvey"));
        assertEquals(0, glm.getAllGuests().size());
        assertEquals(0, glm.getGuestMap().size());
    }

    @Test
    void testRemoveNonExistentGuest() {
        GuestListManager glm = new GuestListManager();
        assertFalse(glm.removeGuest("Harvey"));
    }

    @Test
    void testFindGuest() {
        Guest harvey = new Guest("Harvey", "Neighbor");
        GuestListManager glm = new GuestListManager();
        glm.addGuest(harvey);
        Guest found = glm.findGuest("Harvey");
        assertEquals(harvey, found);
    }

    @Test
    void testFindNonExistentGuest() {
        GuestListManager glm = new GuestListManager();
        assertNull(glm.findGuest("Harvey"));
    }

    @Test
    void testGetGuestCount() {
        Guest harvey = new Guest("Harvey", "Neighbor");
        GuestListManager glm = new GuestListManager();
        glm.addGuest(harvey);
        assertEquals(1, glm.getGuestCount());
    }
}
