import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.math.BigInteger;

public class Main {

    private static char getLetterOutRangeNumber(long num) {
        return (char) ((num - 10) + 'A');
    }

    private static int getNumberFromLetter(char letter) {
        return (int) letter - 'A' + 10;
    }

    private static BigInteger convertToDecimal(String number, int fromBase) {
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < number.length(); i++) {
            long val;
            char curSymbol = number.charAt(i);
            if (curSymbol >= 'A') {
                val = (int) number.charAt(i) - 'A' + 10;
            } else {
                val = Integer.parseInt(((Character) curSymbol).toString());
            }
            val *= Math.round(Math.pow(fromBase, number.length() - i - 1));
            sum = sum.add(BigInteger.valueOf(val));
        }
        return sum;
    }

    private static String convertDecimalToAnySystem(BigInteger number, int base) { // decimal system to any system
        StringBuilder result = new StringBuilder();
        while (true) {
            BigInteger fraction =  number.remainder(BigInteger.valueOf(base)); //number % base;
            if (base >= 11 && fraction.compareTo(BigInteger.valueOf(9l)) > 0) {
                char symbolBit  = getLetterOutRangeNumber(fraction.longValue());
                result.append(symbolBit);
            } else {
                result.append(fraction);
            }
            number = number.divide(BigInteger.valueOf(base));

            if (number.compareTo(BigInteger.valueOf(base)) < 0) // number < base
                break;
        }

        if (!(number.compareTo(BigInteger.valueOf(0)) == 0)) {
            if (number.compareTo(BigInteger.valueOf(9)) > 0) { //number > 9
                result.append(getLetterOutRangeNumber(number.longValue()));
            } else {
                result.append(number);
            }
        }
        return result.reverse().toString();
    }

    public static String convertFractionDecimalToAnySystem(BigDecimal number, int toBase) { // format 0.000
        StringBuilder res = new StringBuilder();
        while (number.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) != 0 && res.length() < 5) {
            number = number.multiply(BigDecimal.valueOf(toBase));
            BigDecimal wholeNum = number.setScale(0, RoundingMode.DOWN);
            long longWholeNum = Long.parseLong(wholeNum.toString());
            if (longWholeNum > 9) {
                res.append(getLetterOutRangeNumber(longWholeNum));
            } else {
                res.append(wholeNum);
            }
            number = number.remainder(BigDecimal.ONE);
        }
        while (res.length() < 5) {
            res.append("0");
        }
        return res.toString();
    }

    public static BigDecimal convertAnySystemToFractionDecimal(String number, Integer fromBase) { // format 0.000
        number = number.substring(2);
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < number.length(); i++) {
            Character curSym = number.charAt(i);
            BigDecimal val;
            if (curSym >= 'A') {
                val = BigDecimal.valueOf(getNumberFromLetter(curSym));
            } else {
                val = BigDecimal.valueOf(Long.parseLong(curSym.toString()));
            }
            Double rightPart = 1 / Math.pow(fromBase, i + 1);
            sum = sum.add(val.multiply(new BigDecimal(rightPart.toString())));
        }
        return sum.setScale(5, RoundingMode.DOWN);
    }

    public static String convertAnyBaseToAnyBase(int fromBase, int toBase, String number) {
        if (number.contains(".")) {
            String[] partOfNumbers = number.split("\\.");

            BigInteger wholeNumberInDecimal = convertToDecimal(partOfNumbers[0], fromBase);
            String anySystemWholePart = convertDecimalToAnySystem(wholeNumberInDecimal, toBase);

            BigDecimal fractionInDecimal = convertAnySystemToFractionDecimal("0." + partOfNumbers[1], fromBase);
            String anySystemFractionPart =  convertFractionDecimalToAnySystem(fractionInDecimal, toBase);

            return anySystemWholePart + "." + anySystemFractionPart;
        } else {
            BigInteger numberInDecimal = convertToDecimal(number, fromBase);
            return convertDecimalToAnySystem(numberInDecimal, toBase);
        }
    }

    public static void twoLevelMenu(int fromBase, int toBase) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter number in base " +  fromBase + " to convert to base " + toBase +  " (To go back type /back) ");
            String inp = scanner.nextLine();
            if (inp.equals("/back")) {
                break;
            } else {
                String resultConvert = convertAnyBaseToAnyBase(fromBase, toBase, inp.toUpperCase());
                System.out.println("Conversion result: " + resultConvert + "\n");
            }
        }
    }

    public static void mainLevelMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            String[] inp = scanner.nextLine().split(" ");
            if (inp.length < 2 && inp[0].equals("/exit")) {
                System.exit(0);
            } else {
                twoLevelMenu(Integer.parseInt(inp[0]), Integer.parseInt(inp[1]));
            }
        }
    }

    public static void main(String[] args) {
        mainLevelMenu();
    }
}