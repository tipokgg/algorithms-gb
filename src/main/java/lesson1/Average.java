package lesson1;

public class Average {

    public static void main(String[] args) {

        int[] arr = {46, 74, 456, 71, 3, 23, 16, 79, 1000};

        System.out.println(linear(arr));
    }

    public static double linear(int[] arr) { // линейная сложность O(n), складыаем все числа массива

        int sum = arr[0];

        for (int i = 1; i < arr.length; i++) {
            sum += arr[i];
        }

        return sum * 1.0 / arr.length;

    }

}
