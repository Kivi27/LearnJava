package com.company;

import java.util.Scanner;

public class Main {

    private static int n;
    private static int m;
    private static char[][] gameField;

    public static void convertStringToGameField(String str) {
        int indexSymbol = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char sym = str.charAt(indexSymbol);
                if (sym == '_') {
                    sym = ' ';
                }
                gameField[i][j] = sym;
                indexSymbol++;
            }
            if (indexSymbol == str.length())
                break;
        }
    }

    private static void printHorizontalLine() {
        for (int i = 0; i < n * m; i++) {
            System.out.print("-");
        }
    }

    public static void printField() {
        printHorizontalLine();
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.print("| ");
            for (int j = 0; j < m; j++) {
                System.out.print(gameField[i][j] + " ");
            }
            System.out.println("|");
        }
        printHorizontalLine();
        System.out.println();
    }

    private static int getCountSymbol(char symbol) {
        int countSymbol = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (gameField[i][j] == symbol) {
                    countSymbol++;
                }
            }
        }
        return  countSymbol;
    }

    public static String analyzeField()
    {
        int countX = getCountSymbol('X');
        int countO = getCountSymbol('O');
        int countFree = n * m - countX - countO;
        boolean isWinX = checkWin('X');
        boolean isWinO = checkWin('O');
        if (isWinX && isWinO || Math.abs(countX - countO) > 1) {
            return "Impossible";
        } else if (isWinX) {
            return "X wins";
        } else if (isWinO) {
            return "O wins";
        } else if (countFree == 0) {
            return "Draw";
        } else {
            return "Game not finished";
        }
    }


    private static boolean checkWin(char symbol) {
        int countSymbol;
        for (int i = 0; i < n; i++) { // check row
            countSymbol = 0;
            for (int j = 0; j < m; j++) {
                if (gameField[i][j] == symbol)
                    countSymbol++;
            }
            if (countSymbol == n)
                return true;
        }

        for (int i = 0; i < n; i++) { // check Column
            countSymbol = 0;
            for (int j = 0; j < m; j++) {
                if (gameField[j][i] == symbol)
                    countSymbol++;
            }
            if (countSymbol == m)
                return true;
        }
        countSymbol = 0;
        for (int i = 0; i < n; i++) { // check other diagonal
            if (gameField[i][i] == symbol) {
                countSymbol++;
            }
            if (countSymbol == n)
                return  true;
        }
        countSymbol = 0;
        for (int i = 0; i < n; i++) {
            if (gameField[i][n - 1 - i] == symbol) { // check other diagonal
                countSymbol++;
            }
            if (countSymbol == n)
                return true;
        }
        return false;
    }

    private static boolean checkStringInNumber(String str) {
        for (int i = 0; i < str.length(); i++)
            if (!Character.isDigit(str.charAt(i)))
                return false;
        return true;
    }

    private static boolean isCellFree(int row, int column) {
        return gameField[row][column] == ' ';
    }

    private static boolean coordinationNotInRange(int row, int column) {
        return  row < 0 || row > 2 || column < 0 || column > 2;
    }

    private static boolean InputNewCoordinate(char playerSymbol)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the coordinates: ");
        String rowText = scanner.next();
        if (!checkStringInNumber(rowText)) {
            System.out.println("You should enter numbers!");
            return false;
        }
        String columnText = scanner.next();
        if (!checkStringInNumber(columnText)) {
            System.out.println("You should enter numbers!");
            return false;
        }

        int row = Integer.parseInt(rowText) - 1;
        int column = Integer.parseInt(columnText) - 1;
        if (coordinationNotInRange(row, column)) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }
        else if (!isCellFree(row, column)) {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }
        else {
            gameField[row][column] = playerSymbol;
            return true;
        }
    }

    public static void main(String[] args) {
        n = 3;
        m = 3;
        gameField = new char[n][m];

        String allCells = "_________"; // empty field
        convertStringToGameField(allCells);
        printField();
        char playerSymbol = 'X';
        while (true) {
            boolean isCorrectInput = false;
            while (!isCorrectInput) {
                isCorrectInput = InputNewCoordinate(playerSymbol);
            }
            printField();
            String finalResult = analyzeField();
            if (finalResult.equals("Draw") || finalResult.contains("win")) {
                System.out.println(finalResult);
                break;
            }
            playerSymbol = playerSymbol == 'X' ? 'O' : 'X';
        }
    }
}
