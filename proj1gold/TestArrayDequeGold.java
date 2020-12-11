import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Random;

/**
 * @author
 * @create 2020-12-05 11:53
 */
public class TestArrayDequeGold {

    @Test
    public void testAdd(){
        Random random = new Random();

        StudentArrayDeque<Integer> studentArrayDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> arrayDequeSolution = new ArrayDequeSolution<>();
        for (int i = 0; i < 100; i++) {
            int r = random.nextInt(100);
            studentArrayDeque.addFirst(r);
            arrayDequeSolution.addFirst(r);
            assertEquals(arrayDequeSolution.get(i),studentArrayDeque.get(i));
        }

    }
}
