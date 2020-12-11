package synthesizer;

/**
 * @author
 * @create 2020-12-11 13:36
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;
}
