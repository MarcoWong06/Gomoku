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
    public int[][] board;

    public Gomoku(int boardLength) {
        this.bLen = boardLength;
        this.board = new int[bLen][bLen];
    }

    public void gameStart() {
        int x, y, player = 1;
        // gameInit();
        while (true) {
            clearPrint();
            printBoard();
            if (isEnd()) return;
            do {
                printInfo(player);
                x = sc.nextInt();
                y = sc.nextInt();
            } while (!isInRange(x, y) || !isVaild(x, y));
            placePiece(player, x, y);
            player = 3 - player;
        }
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

    public boolean isEnd() {
        
        return false;
    }

    public static void clearPrint() {
        System.out.print("\033[H\033[2J");
    }
    public static void main(String[] args) {
        Gomoku myGomokuGame = new Gomoku(10);
        // myGomokuGame.printBoard();
        myGomokuGame.gameStart();
        // System.out.println(myGomokuGame.isInRange(0, 0));
    }
}
