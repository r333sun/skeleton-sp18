/**
 * @author
 * @create 2020-12-04 17:35
 */
public class OffByN implements CharacterComparator {
    private final int N;

    public OffByN(int N) {
        this.N = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        x = Character.toLowerCase(x);
        y = Character.toLowerCase(y);
        return (x + N == y || x - N == y);
    }
}
