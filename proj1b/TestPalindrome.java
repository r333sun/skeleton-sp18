import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
   // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    } //Uncomment this class once you've created your Palindrome class.

    @Test
    public void testIsPalindrome(){
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("terret"));
        assertTrue(palindrome.isPalindrome("rotator"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse(palindrome.isPalindrome("aaab"));
        assertFalse(palindrome.isPalindrome("coffee"));
    }

    @Test
    public void testIsPalindromeOffByOne(){
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake",cc));
        assertTrue(palindrome.isPalindrome("benda",cc));
        assertTrue(palindrome.isPalindrome("climb",cc));
        assertFalse(palindrome.isPalindrome("noon",cc));
        assertFalse(palindrome.isPalindrome("terret",cc));
        assertFalse(palindrome.isPalindrome("rotator",cc));

    }

    @Test
    public void testIsPalindromeOffByN(){
        CharacterComparator cc = new OffByN(2);
        assertTrue(palindrome.isPalindrome("flake",cc));
        assertTrue(palindrome.isPalindrome("benda",cc));
        assertTrue(palindrome.isPalindrome("climb",cc));
        assertFalse(palindrome.isPalindrome("noon",cc));
        assertFalse(palindrome.isPalindrome("terret",cc));
        assertFalse(palindrome.isPalindrome("rotator",cc));

    }
}
