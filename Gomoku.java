/* 
 * Copyright (c) 2024, WONG Sai Lung. All rights reserved.
 * 
 * File: Gomoku.java
 * Author: WONG, Sai Lung
 */
import java.util.Scanner;

public class Gomoku {
    public static Scanner sc = new Scanner(System.in);

    public int bLen; // board length
    public int inARow; // number in a row to win
    public int[][] board;

    /**
     * Before start the game, create a board {@code object}.
     * 
     * Datermind the the board length {@code n * n} and decide the win condition.
     * 
     * <p> For example, create a {@code 10 * 10} board
     * with the win condition {@code 4 in a row}
     * 
     * {@snippet :
     * Gomoku myGomokuGame = new Gomoku(10, 4);
     * myGomokuGame.gameStart();
     * }
     * @param boardLength   board length of a {@code n * n} board
     * @param inARow        number in a row to win
     */
    public Gomoku(int boardLength, int inARow) {
        this.bLen = boardLength;
        this.board = new int[bLen][bLen];
        this.inARow = inARow;
    }

    /**
     * After the board {@code object} created, start the game-loop.
     */
    public void gameStart() {
        int x = 0, y = 0, player = 2;
        printBoard();
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

    /**
     * In game-loop, board {@code object} will print out at console.
     */
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

    /**
     * In game-loop, print out the wave infomation to let {@code player}
     * know who is going to place piece.
     * @param player    who's turn
     */
    public void printInfo(int player) {
        System.out.println("Player " + player + "'s turn.");
        System.out.print("Enter row and column (e.g., 0 1): ");
    }

    /**
     * Return a {@code Boolean} value to datermind is the {@code piece (x, y)}
     * in range of board {@code object}.
     * 
     * In addition, console will print out the err message if the condition is {@code false}.
     * @param x {@code row} of the piece
     * @param y {@code col} of the piece
     * @return  {@code boolen} value o datermind is the {@code piece (x, y)}
     *          in range of board {@code object}.
     */
    public boolean isInRange(int x, int y) {
        if (isPieceInBoard(x, y)) return true;
        System.out.println("Out of range! Try again.");
        return false;
    }

    /**
     * Return a {@code Boolean} value to datermind is the {@code piece (x, y)}
     * be able to place on board {@code object}. The condition will verifly
     * {@code board[x][y]} is empty.
     * 
     * In addition, console will print the err message if the condition is {@code false}.
     * @param x {@code row} of the piece
     * @param y {@code col} of the piece
     * @return  a {@code Boolean} value to datermind is the {@code piece (x, y)}
     *          be able to place on board {@code object}
     */
    public boolean isVaild(int x, int y) {
        if (board[x][y] == 0) return true;
        System.out.println("Invalid move. Try again.");
        return false;
    }

    /**
     * Place a piece on board {@code odject} in the index of {@code (x, y)}.
     * @param player    who place the piece
     * @param x         {@code row} of the piece
     * @param y         {@code col} of the piece
     */
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