/* 
 * Copyright (c) 2024, WONG Sai Lung. All rights reserved.
 * 
 * File: Gomoku.java
 * Author: WONG, Sai Lung
 */
import java.util.Scanner;

public class Gomoku {
    public static Scanner sc = new Scanner(System.in);

    public int bLen;
    public int inARow;
    public int[][] board;

    public Gomoku(int boardLength, int inARow) {
        this.bLen = boardLength;
        this.board = new int[bLen][bLen];
    }

    public void gameStart() {
        int x = 0, y = 0, player = 2;
        printBoard();
        // gameInit();
        do {
            player = 3 - player;
            do {
                printInfo(player);
                x = sc.nextInt();
                y = sc.nextInt();
            } while (!isInRange(x, y) || !isVaild(x, y));
            placePiece(player, x, y);
            clearPrint();
            printBoard();
        } while (!isEnd(x, y));
        System.out.println("Player " + player + " wins!");
    }

    public void gameInit() {
        for (int i = 0; i < bLen; i++) for (int j = 0; j < bLen; j++) board[i][j] = 0;
    }

    public void printBoard() {
        for (int i = 0; i < bLen; i++) {
            System.out.print(i + (i < 10 ? " |" : "|"));
            for (int j : board[i]) System.out.print(" " + j);
            System.out.println();
        }
        System.out.print("  +");
        for (int i = 0; i < bLen; i++) System.out.print("--");
        System.out.println();
        System.out.print("   ");
        for (int i = 0; i < bLen; i++) System.out.print((i < 10 ? " " : "") + i);
        System.out.println();
    }

    public void printInfo(int player) {
        System.out.println("Player " + player + "'s turn.");
        System.out.print("Enter row and column (e.g., 0 1): ");
    }

    public boolean isInRange(int x, int y) {
        if (x >= 0 && x < bLen && y >= 0 && y < bLen) return true;
        System.out.println("Out of range! Input again.");
        return false;
    }

    public boolean isVaild(int x, int y) {
        if (board[x][y] == 0) return true;
        System.out.println("Invalid move. Try again.");
        return false;
    }

    public void placePiece(int player, int x, int y) {
        board[x][y] = player;
    }

    public boolean isEnd(int x, int y) {
        return 
            isHorizontalSame(x, y, inARow) ||
            isVerticalSame(x, y, inARow) ||
            isDiagonalUL2DRSame(x, y, inARow) ||
            isDiagonalDL2URSame(x, y, inARow);
    }

    public boolean isHorizontalSame(int x, int y, int noOfSame) {
        int n = 1;
        for (int i = 0; n < noOfSame && y + i + 1 < bLen && board[x][y + i] == board[x][y + i++ + 1]; ++n) {}
        for (int i = 0; n < noOfSame && y - i - 1 >= 0 && board[x][y - i] == board[x][y - i++ - 1]; ++n) {}
        return n >= noOfSame;
    }

    public boolean isVerticalSame(int x, int y, int noOfSame) {
        int n = 1;
        for (int i = 0; n < noOfSame && x + i + 1 < bLen && board[x + i][y] == board[x + i++ + 1][y]; ++n) {}
        for (int i = 0; n < noOfSame && x - i - 1 >= 0 && board[x - i][y] == board[x - i++ - 1][y]; ++n) {}
        return n >= noOfSame;
    }

    public boolean isDiagonalUL2DRSame(int x, int y, int noOfSame) {
        int n = 1;
        for (int i = 0; n < noOfSame && x + i + 1 < bLen && y + i + 1 < bLen && board[x + i][y + i] == board[x + i + 1][y + i++ + 1]; ++n) {}
        for (int i = 0; n < noOfSame && x - i - 1 >= 0 && y - i - 1 >= 0 && board[x - i][y - i] == board[x - i - 1][y - i++ - 1]; ++n) {}
        return n >= noOfSame;
    }

    public boolean isDiagonalDL2URSame(int x, int y, int noOfSame) {
        int n = 1;
        for (int i = 0; n < noOfSame && x + i + 1 < bLen && y - i - 1 >= 0 && board[x + i][y - i] == board[x + i + 1][y - i++ - 1]; ++n) {}
        for (int i = 0; n < noOfSame && x - i - 1 >= 0 && y + i + 1 < bLen && board[x - i][y + i] == board[x - i - 1][y + i++ + 1]; ++n) {}
        return n >= noOfSame;
    }

    public static void clearPrint() {
        System.out.print("\033[H\033[2J");
    }
    public static void main(String[] args) {
        clearPrint();
        // Gomoku GomokuGame = new Gomoku(15, 5);
        Gomoku myGomokuGame = new Gomoku(10, 4);
        // myGomokuGame.printBoard();
        myGomokuGame.gameStart();
        // System.out.println(myGomokuGame.isInRange(0, 0));
    }
}
