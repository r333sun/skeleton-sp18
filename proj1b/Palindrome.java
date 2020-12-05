/**
 * @author
 * @create 2020-12-04 15:33
 */
public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new ArrayDeque();
        for (int i = 0; i < word.length(); i++) {
            result.addLast(word.charAt(i));
        }
        return result;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        String reverse = "";
        while (!deque.isEmpty()) {
            reverse += deque.removeLast();
        }
        return reverse.equals(word);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int totalSize = word.length() - 1;
        for (int i = 0; i < word.length() / 2; i++) {
            if (!cc.equalChars(word.charAt(i), word.charAt(totalSize - i))) {
                return false;
            }
        }
        return true;
    }

}
