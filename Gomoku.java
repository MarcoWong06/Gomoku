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
        this.inARow = inARow;
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
            // clearPrint();
            printBoard();
        } while (!isEnd(x, y));
        System.out.println("Player " + player + " wins!");
    }

    public void gameInit() {
        for (int i = 0; i < bLen; i++) for (int j = 0; j < bLen; j++) board[i][j] = 0;
    }

    public void printBoard() {
        System.out.println();
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
        if (isPieceInBoard(x, y)) return true;
        System.out.println("Out of range! Try again.");
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
            isAdjacentPiecesSame(x, y, 1, 0) ||
            isAdjacentPiecesSame(x, y, 0, 1) ||
            isAdjacentPiecesSame(x, y, 1, 1) ||
            isAdjacentPiecesSame(x, y, -1, 1);
    }

    public boolean isAdjacentPiecesSame(int x, int y, int dir_x, int dir_y) {
        int noOfPieces = 1;
        for (int i = 0; noOfPieces < inARow && isPieceInBoard(x + (i + 1) * dir_x, y + (i + 1) * dir_y) && board[x + i * dir_x][y + i * dir_y] == board[x + (i + 1) * dir_x][y + (i + 1) * dir_y]; ++i) ++noOfPieces;
        for (int i = 0; noOfPieces < inARow && isPieceInBoard(x - (i + 1) * dir_x, y - (i + 1) * dir_y) && board[x - i * dir_x][y - i * dir_y] == board[x - (i + 1) * dir_x][y - (i + 1) * dir_y]; ++i) ++noOfPieces;
        return noOfPieces >= inARow;
    }

    public boolean isPieceInBoard(int x, int y) {
        return x >= 0 && x < bLen && y >= 0 && y < bLen;
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

/*
-1 0 
0 10 
0 1 
0 1 
0 2 
1 1 
1 2 
2 3 
2 2 
3 1 
2 1 
3 2 
3 0 
3 3 
0 3 

 */