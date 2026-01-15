package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Task;
import java.util.*;

public class TaskManager {
    private final Deque<Task> upcoming = new LinkedList<>();
    private final Stack<Task> completed = new Stack<>();

    public void addTask(Task task) {
        upcoming.add(task);
    }

    public Task executeNextTask() {
        if (!upcoming.isEmpty()){
            Task currentTask = upcoming.poll();
            completed.push(currentTask);
            return currentTask;
        }
        else {
            return null;
        }
    }

    public Task undoLastTask() {
        if (!completed.isEmpty()) {
            Task currentTask = completed.pop();
            upcoming.addLast(currentTask);
            return currentTask;
        }
        else {
            return null;
        }
    }

    public int remainingTaskCount() {
        return upcoming.size();
    }

    public Queue<Task> getUpcomingTasks() {
        return upcoming;
    }
    public Stack<Task> getCompletedTasks() {
        return completed;
    }
}
