# Event Planner Mini

This project demonstrates practical use of data structures:
linked lists, stacks, queues, maps, trees, sorting, and searching.

## Data Structures Used
- A stack in task manager to hold completed tasks
- A dequeue in task manager to hold upcoming tasks
- A linked list guest manager to hold guests
- A hash map in guest list manager to map guests by name for faster lookup
- A tree map in venue selector to sort venues by their price
- A hash map in seating planner to map guests by thier tags
- An array list in seating planner to count the guests in each group

## Searching and Sorting Algorithms
- I used a binary sort algorithm in venue selector because it used a tree map.
- A merge sort was used in the generate seating method of the seating planner class.
After checking that there were more groups than tables, the method converts each group to an array list 
and calls List.sort to put the groups in sorted order. The List class uses a merge sort in its sort() method to sort more efficiently.



## Big-O Complexity
- Finding a Guest is 0(1) because guests are hashed by their name in a map
- Selecting a venue is 0(log(n)) because the venues are stored in a tree sorted by their price, 
which is 0(log(n)) average lookup time, with 0(1) best case and 0(n) worst case
- Generating seating is 0(n) because the method loops through all the guests and places them in a map.
Then, it  goes through the groups and counts them, and it might even loop through the 
groups more than once, but there are much less groups than people usually.
Also, the complexity can never increase exponentially because each person or group
is looped through individually, without checking every group/person to all the other ones.
