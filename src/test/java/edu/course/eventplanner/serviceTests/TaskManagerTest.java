package edu.course.eventplanner.serviceTests;

import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.service.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {
    @Test
    void testAddTask() {
        Task task = new Task("Do important things");
        TaskManager tm = new TaskManager();
        tm.addTask(task);
        assertEquals(1, tm.getUpcomingTasks().size());
        assertEquals("Do important things", tm.getUpcomingTasks().peek().getDescription());
    }

    @Test
    void testExecuteNextTask() {
        Task task = new Task("Do important things");
        TaskManager tm = new TaskManager();
        tm.addTask(task);
        assertEquals("Do important things", tm.executeNextTask().getDescription());
        assertEquals(0, tm.getUpcomingTasks().size());
        assertEquals(1, tm.getCompletedTasks().size());
    }

    @Test
    void testExecuteEmptyList() {
        TaskManager tm = new TaskManager();
        assertNull(tm.executeNextTask());
    }

    @Test
    void testUndoLastTask() {
        Task task = new Task("Do important things");
        TaskManager tm = new TaskManager();
        tm.addTask(task);
        tm.executeNextTask();
        assertEquals("Do important things", tm.undoLastTask().getDescription());
        assertEquals(1, tm.getUpcomingTasks().size());
        assertEquals(0, tm.getCompletedTasks().size());
    }

    @Test
    void testUndoEmptyList() {
        TaskManager tm = new TaskManager();
        assertNull(tm.undoLastTask());
    }

    @Test
    void
    testRemainingTestCount() {
        Task task = new Task("Do important things");
        TaskManager tm = new TaskManager();
        tm.addTask(task);
        assertEquals(1, tm.remainingTaskCount());
    }
}
