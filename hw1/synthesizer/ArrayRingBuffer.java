package synthesizer;

import java.util.Iterator;
import java.util.Random;


public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     *
     * @param x
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer Overflow");
        }
        rb[this.last] = x;
        this.last = addOne(this.last);
        this.fillCount++;
    }

    private int addOne(int num) {
        return (num + 1) % capacity;
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public int fillCount() {
        return this.fillCount;
    }


    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T item = rb[this.first];
        rb[this.first] = null;
        this.first = addOne(this.first);
        this.fillCount--;
        return item;
    }


    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[this.first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int pos = first;
        private int count = 0;

        @Override
        public boolean hasNext() {
            return (count < fillCount());
        }

        @Override
        public T next() {
            T item = rb[pos];
            pos = addOne(pos);
            count++;
            return item;
        }
    }
}
