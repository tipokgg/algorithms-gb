package lesson5;

public class Exponentiation {

    private static long rec(int number, int exp) {
        if (exp == 0) return 1; // вовзращаем 1 если степень 0

        if (exp == 1) return number; // возвращаем возводимое число, если степень единица
        // иначе заходим в рекурсию, передавая в метод возводимо число и степень уменьшенную на один
        // когда степень станет равна единице, то вернётся возводимое в степень число
        // и благодаря стеку вызовов умножится само на себя нужно кол-во раз
        else return number * rec(number, --exp);
    }

    public static void main(String[] args) {
        System.out.println(rec(10, 10));
        System.out.println(rec(2, 32));
    }

}
