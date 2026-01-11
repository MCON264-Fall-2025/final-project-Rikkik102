package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.service.TaskManager;
import edu.course.eventplanner.service.VenueSelector;
import edu.course.eventplanner.util.Generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static edu.course.eventplanner.util.Generators.GenerateGuests;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to event planner!");
        Scanner scnr = new Scanner(System.in);
        List<Venue> venues = new ArrayList<>();
        GuestListManager glm = new GuestListManager();
        TaskManager tm = new TaskManager();
        Venue chosen = new Venue("", 0, 0, 0, 0);

        String menuChoice = "0";
        while (!(menuChoice.equals("10"))) {
            printMenu();
            menuChoice = scnr.nextLine();
            switch (menuChoice) {
                case "1":
                    for (Guest g : GenerateGuests(10)) {
                        glm.addGuest(g);
                    }
                    venues = Generators.generateVenues();
                    break;
                case "2":
                    System.out.println("Enter the guest's name:");
                    String name = scnr.nextLine();
                    System.out.println("Enter the guest's tag:");
                    String tag = scnr.nextLine();
                    glm.addGuest(new Guest(name, tag));
                    break;
                case "3":
                    System.out.println("Enter the guest's name:");
                    String newname = scnr.nextLine();
                    if (glm.removeGuest(newname)) {
                        System.out.println("Deletion successful");
                    } else {
                        System.out.println("No guest with that name exists");
                    }
                    break;
                case "4":
                    System.out.println("Enter your budget:");
                    double budget = scnr.nextDouble();
                    System.out.println("Enter the number of guests:");
                    int guestCount = scnr.nextInt();
                    scnr.nextLine();
                    // Make sure the venue list is not empty
                    if (venues.isEmpty()) {
                        System.out.println("No venues available. Load sample data first.");
                        break;
                    }
                    VenueSelector vs = new VenueSelector(venues);
                    Venue selected = vs.selectVenue(budget, guestCount);
                    if (selected != null) {
                        chosen = selected;
                        System.out.println("Your venue is " + chosen.getName());
                        System.out.println("Cost: " + chosen.getCost());
                        System.out.println("Capacity: " + chosen.getCapacity());
                    } else {
                        System.out.println("No venue exists matching your criteria.");
                    }
                    break;
                case "5":
                    SeatingPlanner sp = new SeatingPlanner(chosen);
                    if (sp.generateSeating(glm.getAllGuests()) != null) {
                        System.out.println("Seating plan made successfully");
                    } else {
                        System.out.println("Error: too many guests for the venue");
                    }
                    break;
                case "6":
                    System.out.println("Enter the task:");
                    String description = scnr.nextLine();
                    tm.addTask(new Task(description));
                    break;
                case "7":
                    if (tm.executeNextTask()) {
                        System.out.println("Execution successful");
                    } else {
                        System.out.println("Execution unsuccessful: No tasks waiting to be completed");
                    }
                    break;
                case "8":
                    if (tm.undoLastTask()) {
                        System.out.println("Successfully undid last completed task");
                    } else {
                        System.out.println("Fail: no completed tasks found");
                    }
                    break;
                case "9":
                    System.out.println("Event Summary:");
                    System.out.println("Guest number " + glm.getGuestCount());
                    System.out.println("Venue: " + chosen.getName());
                    System.out.println("Upcoming tasks: " + tm.getUpcomingTasks().size());

                default:
                    System.out.println("Enter an option from the menu");
            }
        }
    }
    public static void printMenu() {
        System.out.println("Please select an option from the menu\n1) Load sample data\n2) Add guest\n3) Remove guest" +
                "\n4) Select venue\n5) Generate seating chart \n6) Add preparation task \n" +
                "7) Execute next task \n8) Undo last task\n9) Print event summary\n10) Exit");
    }
}
