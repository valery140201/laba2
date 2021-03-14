/**
 * @autor Бокша Валерия
 * 3 курс, 61 группа
 * @version 1.0
 *  Основной класс
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите выражение");
        String eq = in.nextLine();
        eq = eq.replaceAll("pi", "3.14"); //замена константой pi
        System.out.println("Введите количество переменных");
        int count = in.nextInt();
        for(int i = 0; i < count; i++){ //замена переменных
            System.out.println("Введите имя переменной");
            String name = in.nextLine();
            name = in.nextLine();
            System.out.println("Введите значение переменной");
            String value = in.nextLine();
            eq = eq.replaceAll(name, value);

        }
        Eval.setStr(eq);
        System.out.println(Eval.parse());
    }
}