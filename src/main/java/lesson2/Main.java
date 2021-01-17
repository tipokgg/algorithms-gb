package lesson2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static class Array {
        private int[] arr;
        private int size;
        private boolean isSorted;

        private Array() {
            isSorted = false;
        }

        public Array(int size) {
            this();
            this.size = size;
            this.arr = new int[size];
        }

        public Array(int... args) {
            this();
            this.size = args.length;
            this.arr = args;
        }

        public Array(boolean isSorted, int... args) {
            this(args);
            this.isSorted = isSorted;
        }

        public int get(int index) {
            if (index >= size || index < 0)
                throw new ArrayIndexOutOfBoundsException("Your index is not correct: " + index);
            return arr[index];
        }

        public void set(int index, int value) {
            arr[index] = value;
            isSorted = false;
        }

        public boolean delete() { // last
            if (size == 0) return false;
            size--;
            return true;
        }

        public boolean delete(int index) {
            if (index >= size || index < 0)
                throw new ArrayIndexOutOfBoundsException("Your index is not correct: " + index);

            System.arraycopy(arr, index + 1, arr, index, size - index - 1);
            size--;
            return true;
        }

        public void append(int value) {
            if (size >= arr.length - 1) {
                int[] temp = arr;
                arr = new int[size * 2];
                System.arraycopy(temp, 0, arr, 0, size);
            }
            arr[size++] = value;
            isSorted = false;
        }

        public boolean isInArray(int value) {
            for (int i = 0; i < this.size; i++) {
                if (this.arr[i] == value) {
                    return true;
                }
            }
            return false;
        }

        public int hasValue(int value) {
//            if (!isSorted)
//                throw new RuntimeException("Trying to search in unsorted array");

            int l = 0;
            int r = size;
            int m;
            while (l < r) {
                m = (l + r) >> 1;
                if (value == arr[m]) {
                    return m;
                } else {
                    if (value < arr[m]) {
                        r = m;
                    } else {
                        l = m + 1;
                    }
                }
            }
            return -1;
        }

        private void swap(int a, int b) {
            int tmp = this.arr[a];
            this.arr[a] = this.arr[b];
            this.arr[b] = tmp;
        }

        public void sortBubble() { // сложность O(n^2), так как в худшем случае в цикле while мы сделаем n итераций

            // добавляем зависимость цикла от флага сортировки
            // сортируем, пока флаш false
                while (!isSorted) {
                    isSorted = true; // перед началом устанавиваем флаг в true
                    for (int in = 1; in < size; in++) {
                        // и проверяем есть ли элементы которые можно поменять местами
                        if (this.arr[in] < arr[in - 1]) {
                            swap(in, in - 1);
                            // если перестановка произошла, то меняем флаг на false
                            // если перестановок не было, то while цикл закончится
                            isSorted = false;
                        }
                    }
                }
        }

        public void sortSelect() { // сложность 0(n^2), т.к. использутся 2 цикла, один из которых вложен в другой
            for (int i = 0; i < size; i++) {
                int flag = i;
                for (int j = i + 1; j < size; j++) {
                    if (arr[j] < arr[flag])
                        flag = j;
                }
                swap(i, flag);
            }
            isSorted = true;
        }

        public void sortInsert() { // сложность также O(n^2), использутся 2 циккла, в худшем случае whine даст n итераций
            for (int out = 1; out < size; out++) {
                int temp = arr[out];
                int in = out;
                while (in > 0 && arr[in - 1] >= temp) {
                    arr[in] = arr[in - 1];
                    in--;
                }
                arr[in] = temp;
            }
            isSorted = true;
        }

        // homework

        // boolean deleteAll(int value) { } // by value
        boolean deleteAll(int value) {

            // false если такого значения нет в массиве или массив пустой
            if (hasValue(value) == -1 || size == 0) return false;

            for (int i = 0; i < size; i++) { // в цикле проходим по массиву
                while (this.arr[i] == value) {
                    // если находим индекс где есть нужно значение, то удаляем его и сдвигаем массив влево,
                    // пока на этом индексе
                    if (i != size-1) delete(i);
                    else {  // при этом если это последний элемент, то удаляем методом delete() и выходим из цикла
                        delete();
                        break;
                    }
                }
            }
            return true;
        }


        // boolean deleteAll() { } // clear
        public boolean deleteAll() {
            if (size == 0) return false; // false если массив пустой

            this.size = 0; // устанавиливаем длину на 0
            this.isSorted = false; // сбраслывам флаг сортировки
            this.arr = new int[0]; // создаем новый массив нулевой длины и записываем в arr
            return true;
        }

        // void insert(int index, int value) { } // shift the tail
        public void insert(int index, int value) {
            if (index >= size || index < 0) // проверяем корректность индекса
                throw new ArrayIndexOutOfBoundsException("Your index is not correct: " + index);

            this.isSorted = false; // сбрасываем флаг сортировки

            int[] tmp = this.arr; // записываем ссылку на массив во временную переменную
            this.arr = new int[size*2];// в arr записываем новый массив с длиной х2
            this.arr[index] = value; // в нужный индекс записываем значение
            if (index != 0) {
                // копируем левую часть старого массива в новый
                System.arraycopy(tmp, 0, this.arr, 0, index);
            }
            // копируем правую часть старого массива в новый
            System.arraycopy(tmp, index, this.arr, index+1, size-index);
            this.size++; // увеличиваем размер на 1
        }


        @Override
        public String toString() {
            if (arr == null)
                return "null";
            int iMax = size - 1;
            if (iMax == -1)
                return "[]";

            StringBuilder b = new StringBuilder();
            b.append('[');
            for (int i = 0; ; i++) {
                b.append(arr[i]);
                if (i == iMax)
                    return b.append(']').toString();
                b.append(", ");
            }
        }
    }

    public static void main(String[] args) {
        Array array = new Array(0,1,2,4,3,9,-10,14,12,15,14);
//        Array array = new Array(0,1,2,4,3);
        System.out.println(array);
        array.sortBubble();
        System.out.println(array);
//        System.out.println(array);
//        array.sortInsert();
//        System.out.println(array);
//
//        array.insert(2, 11);
//        array.insert(0, 14);
//        array.insert(10, 80);
//        array.deleteAll(0);
//        System.out.println(array);
//
//        array.deleteAll(7);
//        System.out.println(array);
//
//        array.deleteAll(8);
//        System.out.println(array);
//
//        array.deleteAll(9);
//        System.out.println(array);

    }
}