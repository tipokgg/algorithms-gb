package lesson1;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Exponentiation {

    public static void main(String[] args) {
        int num = 2;

        System.out.println(linear(num, 10));
        System.out.println(binary(num, 16));

    }

    private static long linear(int number, int exp) { // сложность On. Последовательно умножаем число друг на друга n раз.

        if (exp == 0) return 1;
        if (exp == 1) return number;

        long result = number;

        for (int i = 0; i < exp - 1; i++) {
            result = result * number;
        }

        return result;
    }

    // сложность O(log2n)
    // при каждом "погружении" в рекурсию количество оставшихся умножений будет уменьшаться в 2 раза
    private static long binary(int number, int exp) {
        if (exp == 0) return 1;
        if (exp == 1) return number;

        if (exp % 2 == 1) // нечётная степень
            return binary(number, exp-1) * number;
        else { // четная степень
            long b = binary(number, exp/2);
            return b * b;
        }
    }

}
