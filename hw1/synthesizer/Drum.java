package synthesizer;

import java.util.HashSet;
import java.util.Set;

//Make sure this class is public
public class Drum {
    /**
     * Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday.
     */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = 1; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public Drum(double frequency) {
        int capacity = (int) (SR / frequency);
        buffer = new ArrayRingBuffer<>(capacity);
        for (int i = 0; i < capacity; i++) {
            buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        Set<Double> numbers = new HashSet<>();
        while (numbers.size() < buffer.capacity()) {
            numbers.add(Math.random() - 0.5);
        }
        while (!buffer.isEmpty()) {
            buffer.dequeue();
        }
        for (Double num : numbers) {
            buffer.enqueue(num);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        Double first = buffer.dequeue();
        Double second = buffer.peek();
        Double avg = (first + second) * 0.5 * DECAY;
        double r = Math.random();
        if(r < 0.5){
            avg = -avg;
        }
        buffer.enqueue(avg);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        if (!buffer.isEmpty()) {
            return buffer.peek();
        }
        return 0;
    }
}
