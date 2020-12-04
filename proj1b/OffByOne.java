/**
 * @author
 * @create 2020-12-04 17:02
 */
public class OffByOne implements CharacterComparator{
    @Override
    public boolean equalChars(char x, char y) {
        return (x==y+1||x+1 == y);
    }

}
