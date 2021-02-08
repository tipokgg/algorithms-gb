package lesson8;

public class End {

    public static void main(String[] args) {
        print("Спасибо за курс!");
    }

    public static void print(String text) {
        System.out.println(text);
        print(text);
    }
}

