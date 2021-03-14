/**
 * @autor Бокша Валерия
 * 3 курс, 61 группа
 * @version 1.0
 * Класс парсера
 */
public class Eval {

    /** Поле позиции */
    static int pos = -1;

    /** Поле обрабатываемого символа */
    static char ch;

    /** Поле текущего значения строки */
    static String str;

    /**
     * Геттер-метод
     * @return текущее str из поля
     */
    static public String getStr() {
        return str;
    }

    /**
     * Сеттер-метод
     * @param newStr - некоторое строковое значение
     * Присваивает newStr переменной str
     */
    static public void setStr(String newStr) {
        str = newStr;
    }

    /**
     * Если строка не закончилась
     * Присвоить символ текущей позиции переменной ch
     * иначе присвоить "!"
     */
    static void nextChar() {
        ch = (++pos < getStr().length()) ? getStr().charAt(pos) : '!' ;
    }

    /**
     * @param charToEat - нужный символ в строке
     * @return true если символ совпадает с текущим ch
    false если - не совпадает
     * Пропускает пробелы
     */
    static boolean eat(char charToEat) {
        while (ch == ' ') nextChar();
        if (ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    /**
     * Устанавливает начальную позицию
     * Вызывает метод parseExpression()
     * @return значение parseExpression();
     * @throws RuntimeException позиция не начальная
     */
    static double parse() {
        nextChar();
        double x = parseExpression();
        if (pos < getStr().length()) throw new RuntimeException("Unexpected: " + (char)ch);
        return x;
    }

    /**
     * Вызывает метод parseTerm() для x
     * Выполняет сложение и вычитание со следующим parseTerm()
     * 2-й порядок
     * @return значение parseTerm() если сложение и вычитание не были выполнены
     */
    static double parseExpression() {
        double x = parseTerm();
        for (;;) {
            if      (eat('+')) x += parseTerm(); //сложение
            else if (eat('-')) x -= parseTerm(); //вычитание
            else return x;
        }
    }

    /**
     * Вызывает метод parseFactor() для x
     * Выполняет умножение и деление со следующим parseTerm()
     * 1-й порядок
     * @return если значение parseFactor() умножение и деление не были выполнены
     */
    static double parseTerm() {
        double x = parseFactor();
        for (;;) {
            if      (eat('*')) x *= parseFactor(); //умножение
            else if (eat('/')) x /= parseFactor(); //деление
            else return x;
        }
    }


    /**
     * Определяет знак выражения или числа
     * Определяет и переопределяет начало нового выражения
     * до тех пор, пока не достигнет выражения минимальной длины(заключительные скобки)
     * 0-й порядок
     * Определяет число или функцию и преобразует их в double
     * Рекурсивно вызывается метод parseExpression()
     * @return x - конечное значение выражения
     * @throws RuntimeException если функция или символ неизвестны
     */
    static double parseFactor() {
        if (eat('+')) return parseFactor(); //унарный плюс
        if (eat('-')) return -parseFactor(); //унарный минус

        double x;
        int startPos = pos;
        if (eat('(')) { //скобки
            x = parseExpression();
            eat(')');
        } else if ((ch >= '0' && ch <= '9') || ch == '.') { //числа
            while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
            x = Double.parseDouble(getStr().substring(startPos, pos));
        } else if (ch >= 'a' && ch <= 'z') { //функции
            while (ch >= 'a' && ch <= 'z') nextChar();
            String func = getStr().substring(startPos, pos);
            x = parseFactor();
            if (func.equals("sqrt")) x = Math.sqrt(x);
            else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
            else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
            else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
            else throw new RuntimeException("Unknown function: " + func);
        } else {
            throw new RuntimeException("Unexpected: " + (char)ch);
        }

        if (eat('^')) x = Math.pow(x, parseFactor()); //возведение в степень
        return x;
    }
}