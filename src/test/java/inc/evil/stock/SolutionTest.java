package inc.evil.stock;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class SolutionTest {

    @Test
    public void test6() {
        assertEquals(7, Solution6.solution(new int[][]{
                {0, 1, 1, 0},
                {0, 0, 0, 1},
                {1, 1, 0, 0},
                {1, 1, 1, 0},
        }));
        assertEquals(11, Solution6.solution(new int[][]{
                {0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0},
        }));
    }

    public static class Solution6 {
        public static int solution(int[][] m) {
            return 0;
        }
    }

    @Test
    public void test5() {
//        assertTrue(Solution5.isLuckyTriplet(new int[] {1, 1, 1}));
//        assertTrue(Solution5.isLuckyTriplet(new int[] {1, 2, 4}));
//        assertTrue(Solution5.isLuckyTriplet(new int[] {1, 3, 6}));
//        assertFalse(Solution5.isLuckyTriplet(new int[] {1, 3, 5}));
//        assertFalse(Solution5.isLuckyTriplet(new int[] {1, 2, 5}));
        assertEquals(1, Solution5.solution(new int[]{1, 1, 1}));
        assertEquals(3, Solution5.solution(new int[]{1, 2, 3, 4, 5, 6}));
    }

    public static class Solution5 {
        public static int solution(int[] l) {
            int numberOfLuckyTriplets = 0;
            for (int i = 0; i < l.length - 2; ++i) {
                for (int j = i + 1; j < l.length - 1; ++j) {
                    for (int k = j + 1; k < l.length; ++k) {
                        if (isLuckyTriplet(new int[]{l[i], l[j], l[k]})) {
                            ++numberOfLuckyTriplets;
                        }
                    }
                }
            }
            return numberOfLuckyTriplets;
        }

        public static boolean isLuckyTriplet(int[] triplet) {
            return ((triplet[2] % triplet[1]) == 0) && ((triplet[1] % triplet[0]) == 0);
        }
    }

    @Test
    public void test4() {
        assertEquals(2, Solution4.solution("4"));
        assertEquals(3, Solution4.solution("8"));
        assertEquals(4, Solution4.solution("16"));
        assertEquals(5, Solution4.solution("15"));
        assertEquals(4, Solution4.solution("9"));
        assertEquals(0, Solution4.solution("1"));
        assertEquals(1, Solution4.solution("2"));
        assertEquals(2, Solution4.solution("3"));
        assertEquals(3, Solution4.solution("5"));
        assertEquals(3, Solution4.solution("6"));
        assertEquals(4, Solution4.solution("7"));
        assertEquals(5, Solution4.solution("24"));
        assertEquals(6, Solution4.solution("25"));
        assertEquals(6, Solution4.solution("26"));
        assertEquals(6, Solution4.solution("30"));
        assertEquals(6, Solution4.solution("31"));
        assertEquals(6, Solution4.solution("33"));
        assertEquals(6, Solution4.solution("34"));
        assertEquals(7, Solution4.solution("35"));
    }

    public static class Solution4 {
        public static int solution(String x) {
            int steps = 0;
            BigDecimal n = new BigDecimal(x);
            while (n.compareTo(BigDecimal.ONE) > 0) {
                if (n.remainder(BigDecimal.valueOf(2)).equals(BigDecimal.ZERO)) {
                    n = n.divide(BigDecimal.valueOf(2), RoundingMode.DOWN);
                } else if ((n.equals(BigDecimal.valueOf(3))) || (n.remainder(BigDecimal.valueOf(4)).equals(BigDecimal.ONE))) {
                    n = n.subtract(BigDecimal.ONE);
                } else {
                    n = n.add(BigDecimal.ONE);
                }
                ++steps;
            }
            return steps;
        }

        public static int recursiveSolution(String x) {
            long n = Long.parseLong(x);
            if (n == 1) {
                return 0;
            } else if (n % 2 == 0) {
                return 1 + solution(String.valueOf(n / 2));
            }
            return 1 + Math.min(solution(String.valueOf(n - 1)), solution(String.valueOf(n + 1)));
        }
//        public static int solution(String x) {
//            long n = Long.parseLong(x);
//            int numberOfSteps = 0;
//            boolean isPowerOf2 = isPowerOf2(n);
//            if (isPowerOf2) {
//                while (n > 1) {
//                    ++numberOfSteps;
//                    n /= 2;
//                }
//            } else {
//                int stepsByGoingToNearestPowerOf2 = getNumberOfStepsByFindingNearestPowerOf2(n);
//                if (n % 2 == 0) {
//                    int stepsByDividingByTwo = getStepsByDividingByTwo(n);
//                    numberOfSteps = Math.min(stepsByDividingByTwo, stepsByGoingToNearestPowerOf2);
//                } else {
//                    int stepsBySubtracting = 1 + getStepsByDividingByTwo(n - 1);
//                    int stepsByAdding = 1 + getStepsByDividingByTwo(n + 1);
//                    numberOfSteps = Math.min(Math.min(stepsBySubtracting, stepsByAdding), stepsByGoingToNearestPowerOf2);
//                }
//            }
//            return numberOfSteps;
//        }

        private static int getStepsByDividingByTwo(long n) {
            int steps = 0;
            long currentNumber = n;
            while (currentNumber % 2 == 0) {
                currentNumber /= 2;
                ++steps;
            }
            steps += getNumberOfStepsByFindingNearestPowerOf2(currentNumber);
            return steps;
        }

        private static int getNumberOfStepsByFindingNearestPowerOf2(long n) {
            int numberOfSteps = 0;
            double greaterSibling = log2(n + 1);
            double lesserSibling = log2(n - 1);
            if ((!isFloating(greaterSibling) && greaterSibling < lesserSibling) || (getFractionalPart(greaterSibling) < getFractionalPart(lesserSibling))) {
                long currentValue = n;
                while (!isPowerOf2(currentValue)) {
                    ++currentValue;
                    ++numberOfSteps;
                }
                //is power of 2 here
                while (currentValue > 1) {
                    ++numberOfSteps;
                    currentValue /= 2;
                }
            } else {
                long currentValue = n;
                while (!isPowerOf2(currentValue)) {
                    --currentValue;
                    ++numberOfSteps;
                }
                //is power of 2 here
                while (currentValue > 1) {
                    ++numberOfSteps;
                    currentValue /= 2;
                }
            }
            return numberOfSteps;
        }

        private static boolean isPowerOf2(long n) {
            return !isFloating(log2(n));
        }

        private static double getFractionalPart(double d) {
            long n = (long) d;
            return d - n;
        }

        private static boolean isFloating(double d) {
            long n = (long) d;
            double r = d - n;
            return r > 0;
        }

        private static double log2(double x) {
            return Math.log(x) / Math.log(2);
        }
    }
//
//    @Test
//    public void test3() {
//        assertEquals(2, Solution3.solution(">----<"));
//        assertEquals(4, Solution3.solution("<<>><"));
//        assertEquals(10, Solution3.solution("--->-><-><-->-"));
//        assertEquals(0, Solution3.solution("---------------"));
//        assertEquals(0, Solution3.solution("<---------------<"));
//        assertEquals(0, Solution3.solution("<<"));
//        assertEquals(0, Solution3.solution(">--------------->"));
//        assertEquals(4, Solution3.solution(">-->--<"));
//        assertEquals(6, Solution3.solution(">-->>--<"));
//        assertEquals(6, Solution3.solution("<>-->>--<"));
//        assertEquals(6, Solution3.solution("<>-->>--<>"));
//        assertEquals(0, Solution3.solution("<<<<<<<<<<<->>>>>>>>>>>"));
//        assertEquals(4, Solution3.solution("<<>><"));
//    }
//
//    public static class Solution3 {
//        public static int solution(String s) {
//            s = s.replaceAll("-", "");
//            int numberOfGreetings = 0;
//            for (int i = 0; i < s.length(); ++i) {
//                char currentEmployee = s.charAt(i);
//                boolean goingLeft = currentEmployee == '<';
//                if (goingLeft) {
//                    for (int j = i - 1; j >= 0; --j) {
//                        if (isIntersecting(currentEmployee, s.charAt(j))) {
//                            numberOfGreetings += 2;
//                        }
//                    }
//                } else {
//                    for (int j = i + 1; j < s.length(); ++j) {
//                        if (isIntersecting(currentEmployee, s.charAt(j))) {
//                            numberOfGreetings += 2;
//                        }
//                    }
//                }
//            }
//            return numberOfGreetings / 2;
//        }
//
//        private static boolean isIntersecting(char currentEmployee, char colleague) {
//            return currentEmployee != colleague;
//        }
//    }
//
//    @Test
//    public void test2() {
//        assertArrayEquals(new int[]{-1, -1}, Solution2.solution(new int[]{1, 2, 3, 4}, 15));
//        assertArrayEquals(new int[]{0, 2}, Solution2.solution(new int[]{4, 3, 5, 7, 8}, 12));
//        assertArrayEquals(new int[]{2, 3}, Solution2.solution(new int[]{4, 3, 10, 2, 8}, 12));
//        assertArrayEquals(new int[]{2, 2}, Solution2.solution(new int[]{4, 3, 10, 2, 8}, 10));
//        assertArrayEquals(new int[]{0, 3}, Solution2.solution(new int[]{4, 3, 1, 2, 8}, 10));
//        assertArrayEquals(new int[]{3, 4}, Solution2.solution(new int[]{4, 3, 100, 2, 8}, 10));
//        assertArrayEquals(new int[]{4, 4}, Solution2.solution(new int[]{4, 3, 10, 2, 100}, 100));
//        assertArrayEquals(new int[]{0, 0}, Solution2.solution(new int[]{100, 3, 10, 2, 5}, 100));
//        assertArrayEquals(new int[]{0, 1}, Solution2.solution(new int[]{99, 1, 98, 2, 5}, 100));
//        assertArrayEquals(new int[]{0, 2}, Solution2.solution(new int[]{98, 1, 1, 98, 2, 5}, 100)); // (0,2) < (3, 4)
//    }
//
//    public static class Solution2 {
//        public static int[] solution(int[] l, int t) {
//            for (int i = 0; i < l.length; ++i) {
//                int sum = 0;
//                for (int j = i; j < l.length; ++j) {
//                    sum += l[j];
//                    if (sum == t) {
//                        return new int[] {i, j};
//                    }
//                }
//            }
//            return new int[] {-1, -1};
//        }
//    }
//
//
//
//    @Test
//    public void test1() {
//        assertArrayEquals(new int[]{}, Solution1.solution(new int[]{1, 2, 3}, 0));
//        assertArrayEquals(new int[]{1, 4}, Solution1.solution(new int[]{1, 2, 2, 3, 3, 3, 4, 5, 5}, 1));
//        assertArrayEquals(new int[]{5, 15, 7}, Solution1.solution(new int[]{5, 10, 15, 10, 7}, 1));
//        assertArrayEquals(new int[]{1, 2, 2, 4, 5, 5}, Solution1.solution(new int[]{1, 2, 2, 3, 3, 3, 4, 5, 5}, 2));
//    }
//
//    public static class Solution1 {
//        public static int[] solution(int[] data, int n) {
//            if (n <= 0) {
//                return new int[]{};
//            }
//            Map<Integer, Integer> frequencyMap = calculateFrequency(data);
//            List<Integer> result = new ArrayList<>();
//            for (int value : data) {
//                int frequency = frequencyMap.get(value);
//                if (frequency <= n) {
//                    result.add(value);
//                }
//            }
//            return result.stream().mapToInt(i -> i)
//                    .toArray();
//        }
//
//        private static Map<Integer, Integer> calculateFrequency(int[] data) {
//            Map<Integer, Integer> frequencyMap = new LinkedHashMap<>();
//            for (int value : data) {
//                frequencyMap.merge(value, 1, (oldValue, newValue) -> oldValue + 1);
//            }
//            return frequencyMap;
//        }
//    }
}
