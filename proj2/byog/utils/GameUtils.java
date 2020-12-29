package byog.utils;

import byog.Core.RandomUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameUtils {

    public static int GetRandom(List<Integer> l1, List<Integer> l2, Random r) {
        l1.retainAll(l2);
        if (l1.size() == 0) {
            return -1;
        }
        int idx = RandomUtils.uniform(r, l1.size());
        return l1.get(idx);
    }

    /**
     * if [num1,num2] has overlapping part with [num3,num4]
     * @return true if there have common part
     */
    public static boolean isInRange(int num1, int num2, int num3, int num4) {
        if (num3 >= num1 && num3 <= num2) {
            return true;
        }
        if (num4 >= num1 && num4 <= num2) {
            return true;
        }
        if (num1 >= num3 && num1 <= num4) {
            return true;
        }
        return num2 >= num3 && num2 <= num4;
    }

    /**
     * generate a int number in the range of middle of the number
     * @return int
     */
    public static int generateMiddlePoint(int num1, int num2, int num3, int num4, Random random){
       int[] arr = new int[]{num1,num2,num3,num4};
       Arrays.sort(arr);
       arr[1] += 1;
       arr[2] -= 1;
        Arrays.sort(arr);
//        System.out.println(Arrays.toString(arr));
       if(arr[1] == arr[2]){
           return arr[1];
       }
        return RandomUtils.uniform(random,arr[1],arr[2]);
    }

    public static boolean pointInRange(int num, int lower, int upper){
        return num <= upper && num >= lower;
    }

    public static void main(String[] args) {
        String test = "123456:q";
        System.out.println(test.substring(0,test.length()-2));
        System.out.println(test);
    }
}
