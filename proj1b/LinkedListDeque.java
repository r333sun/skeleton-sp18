/**
 * @author
 * @create 2020-11-26 11:23
 */
public class LinkedListDeque<T> implements Deque<T> {

    private Node<T> sentinel = new Node();
    private int size;

    public LinkedListDeque() {
        this.sentinel.prev = sentinel;
        this.sentinel.next = sentinel;
        this.size = 0;
    }

    private static class Node<T> {
        public T value;
        public Node<T> prev;
        public Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public Node() {
        }
    }

    @Override
    public void addFirst(T item) {
        size++;
        Node<T> node = new Node(item, null, null);
        sentinel.next.prev = node;
        node.next = sentinel.next;
        sentinel.next = node;
        node.prev = sentinel;
    }

    @Override
    public void addLast(T item) {
        size++;
        Node<T> node = new Node(item, null, null);
        node.next = sentinel;
        sentinel.prev.next = node;
        node.prev = sentinel.prev;
        sentinel.prev = node;
    }

    @Override
    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node<T> p = sentinel;
        while (p.next != sentinel) {
            p = p.next;
            System.out.print(p.value + " ");
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            size--;
            Node<T> node = sentinel.next;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            return node.value;
        }
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            size--;
            Node<T> node = sentinel.prev;
            sentinel.prev = node.prev;
            node.prev.next = sentinel;
            return node.value;
        }
    }

    @Override
    public T get(int index) {
        if (size - 1 < index) {
            return null;
        } else {
            Node<T> p = sentinel.next;
            while (index >= 0) {
                index--;
                p = p.next;
            }
            return p.value;
        }
    }

    public T getRecursive(int index) {
        if (index < 0 || index > size) {
            return null;
        } else {
            int count = 0;
            Node<T> p = sentinel.next;
            return getRecursiveHelper(index, count, p);
        }
    }

    private T getRecursiveHelper(int index, int count, Node<T> p) {
        if (count == index) {
            return p.value;
        } else {
            return getRecursiveHelper(index, ++count, p.next);
        }
    }
}
