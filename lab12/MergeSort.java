import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     * <p>
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     */
    private static <Item extends Comparable> Queue<Queue<Item>>
    makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> result = new Queue<>();
        for (Item item : items) {
            Queue<Item> singleQueue = new Queue<>();
            singleQueue.enqueue(item);
            result.enqueue(singleQueue);
        }
        return result;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     * <p>
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return A Queue containing all of the q1 and q2 in sorted order, from least to
     * greatest.
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        Queue<Item> result = new Queue<>();
        while (!q1.isEmpty() && !q2.isEmpty()) {
            result.enqueue(getMin(q1, q2));
        }
        return result;
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        if (items.size() == 1 || items.size() == 0) {
            return items;
        }
        Queue<Queue<Item>> singleItemQueues = makeSingleItemQueues(items);
        if (singleItemQueues.size() == 2) {
            return mergeSortedQueues(singleItemQueues.dequeue(), singleItemQueues.dequeue());
        } else {
            Queue<Item> leftQueue = new Queue<>();
            Queue<Item> rightQueue = new Queue<>();
            int halfSize = items.size() / 2;
            for (int i = 0; i < halfSize; i++) {
                leftQueue.enqueue(items.dequeue());
            }
            while (!items.isEmpty()) {
                rightQueue.enqueue(items.dequeue());
            }
            return mergeSortedQueues(mergeSort(leftQueue), mergeSort(rightQueue));
        }
    }

    public static void main(String[] args) {
        Queue<String> students = new Queue<String>();
        students.enqueue("33");
        students.enqueue("46");
        students.enqueue("1");
        students.enqueue("67");
        students.enqueue("67");
        students.enqueue("88");
        students.enqueue("20");
        students = MergeSort.mergeSort(students);
        System.out.println(students.toString());
    }
}
