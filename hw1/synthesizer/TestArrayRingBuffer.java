package synthesizer;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Tests the ArrayRingBuffer class.
 *
 * @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        for (int i = 0; i < 8; i++) {
            arb.enqueue(i);
//            System.out.println(arb);
        }
        for (int i = 0; i < 3; i++) {
            int item = (int) arb.dequeue();
            System.out.println(arb.peek());
            System.out.println("item: " + item + arb);
        }

        for (int i = 0; i < 5; i++) {
            arb.enqueue(i);
            System.out.println(arb);
        }

        arb.dequeue();
        System.out.println(arb);
        for (Object i : arb) {
            System.out.println(i);
        }
    }

    /**
     * Calls tests for ArrayRingBuffer.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
