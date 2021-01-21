package lesson3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class ReverseString {

    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in)); // для чтения строки с клавиатуры

        System.out.print("Enter string for reverse: ");
        String originalString = bf.readLine().trim(); // запрашиваем строку

        System.out.println("Reversed string: " + reverse(originalString)); // возвращаем "перевёрнутый" результат
    }


    private static String reverse(String originalString) {

        Stack<Character> stack = new Stack<>(); // дефолтный стек

        for (int i = 0; i < originalString.length(); i++) {
            stack.push(originalString.charAt(i)); // в цикле записываем в стек посимвольно всю строку
        }

        char[] chars = new char[originalString.length()]; // готовим массив под перевёрнутую строку

        for (int i = 0; i < originalString.length(); i++) {
            chars[i] = stack.pop(); // в цикле забираем из стека символы в массив
        }

        return new String(chars); // возвращаем перевёрнутую строку
    }

}
