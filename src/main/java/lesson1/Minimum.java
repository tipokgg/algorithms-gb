package lesson1;

public class Minimum {

    public static void main(String[] args) {

        int[] arr = {33, 65, 456, 22, 3, 18, 16, 79, 100};

        System.out.println(linearMin(arr));
        System.out.println(bubbleSortMin(arr));

    }

    public static int linearMin(int[] arr) { // линейный поиск, сложность О(n), т.к. проходим по всем элементам массива

        int result = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < result) result = arr[i];
        }

        return result;
    }

    // поиск через пузырьковую сортировку массива. сложность O(n^2),
    // т.к. используются два цикла, один вложен в другой, которые проходят по всему массиву

    public static int bubbleSortMin(int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j] > arr[j+1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
            }
        }

        return arr[0];

    }



}
