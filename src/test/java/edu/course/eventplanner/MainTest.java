package edu.course.eventplanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testProgramStartsAndExitsImmediately() {
        // Input: immediately exit
        String input = "10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains("Welcome to event planner!"));
        assertTrue(output.contains("Please select an option from the menu"));
    }

    @Test
    void testLoadSampleDataOption() {
        // Load sample data, then exit
        String input = "1\n10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains("Welcome to event planner!"));
        assertTrue(output.contains("Please select an option from the menu"));
    }

    @Test
    void testAddAndRemoveGuest() {
        String input =
                "2\nAlice\nfamily\n" +   // add guest
                        "3\nAlice\n" +           // remove guest
                        "10\n";                  // exit

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains("Enter the guest's name:"));
        assertTrue(output.contains("Deletion successful"));
    }

    @Test
    void testRemoveNonexistentGuest() {
        String input =
                "3\nBob\n" +
                        "10\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains("No guest with that name exists"));
    }

    @Test
    void testSelectVenueWithoutLoadingVenues() {
        String input =
                "4\n1000\n10\n" +
                        "10\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains("No venues available. Load sample data first."));
    }

    @Test
    void testTaskExecutionFailureWhenNoTasks() {
        String input =
                "7\n" +
                        "10\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains("Execution unsuccessful"));
    }

    @Test
    void testEventSummaryPrints() {
        String input =
                "1\n" +    // load sample data
                        "9\n" +    // print summary
                        "10\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains("Event Summary:"));
        assertTrue(output.contains("Guest number"));
        assertTrue(output.contains("Venue:"));
        assertTrue(output.contains("Upcoming tasks"));
    }
}
