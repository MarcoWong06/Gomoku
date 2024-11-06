import java.util.Scanner;

/**
 * Copyright (c) 2024, WONG Sai Lung. All rights reserved.
 * @author WONG, Sai Lung
 * @version v1.0.0
 */
public class Gomoku {
    public static final Scanner SC = new Scanner(System.in);

    private final int BOARD_LENGTH; // board length
    private final int IN_A_ROW;     // number in a row to win
    private int[][] board;          // board object

    /**
     * Initializes the Gomoku game with specified board size and win condition.
     * 
     * For example, create a {@code 10 * 10} board with the win condition {@code 4 in a row}
     * 
     * {@snippet:
     * Gomoku myGomokuGame = new Gomoku(10, 4);
     * myGomokuGame.startGame();
     * }
     * @param boardLength   board length of a {@code n * n} board
     * @param IN_A_ROW        number in a row to win
     */
    public Gomoku(int boardLength, int IN_A_ROW) {
        // Error detection
        if (boardLength < 1)
            throw new IllegalArgumentException("Board length must be positive");
        if (IN_A_ROW < 1)
            throw new IllegalArgumentException("In a row value must be positive");

        this.BOARD_LENGTH = boardLength;
        this.IN_A_ROW = IN_A_ROW;
        this.board = new int[BOARD_LENGTH][BOARD_LENGTH];
    }

    /**
     * Initializes a {@code 10 * 10} board 
     * with a win condition of {@code 4 in a row}.
     */
    public Gomoku() {
        this(10, 4);
    }

    /**
     * After the board created, start the game loop.
     */
    public void startGame() {

        /* Initialization */
        int x = 0, y = 0, player = 2;
        printBoard();

        /* Game-loop */
        do {
            player = 3 - player;    // Switch player
            do {                    // A scanner input do-while-loop
                printInfo(player);
                x = SC.nextInt();
                y = SC.nextInt();
            } while (!isInRange(x, y) || !isVaild(x, y));
            placePiece(player, x, y);
            // clearPrint();
            printBoard();
        } while (!isEnd(x, y));

        /* Game end */
        System.out.println("Player " + player + " wins!");
    }

    /**
     * In game-loop, board  will print out at console.
     */
    public void printBoard() {
        System.out.println();

        /* Upper part of board */
        for (int i = 0; i < BOARD_LENGTH; i++) {

            /* row index of board */
            System.out.print(i + (i < 10 ? " |" : "|"));

            /* board piece */
            for (int j : board[i]) System.out.print(" " + j);

            System.out.println();
        }

        /* boundary and column index */
        System.out.print("  +");
        for (int i = 0; i < BOARD_LENGTH; i++)
            System.out.print("--");
        System.out.println();
        System.out.print("   ");
        for (int i = 0; i < BOARD_LENGTH; i++)
            System.out.print((i < 10 ? " " : "") + i);
        System.out.println();
    }

    /**
     * Notifies the {@code player} of their turn and prompts for input.
     * @param player the current player
     */
    public void printInfo(int player) {
        System.out.println("Player " + player + "'s turn.");
        System.out.print("Enter row and column (e.g., 0 1): ");
    }

    /**
     * Return a {@code Boolean} value to datermind is the {@code piece (x, y)}
     * in range of board .
     * 
     * In addition, console will print out the err message if the condition is {@code false}.
     * @param x {@code row} of the piece
     * @param y {@code col} of the piece
     * @return  {@code true} if the piece is in range, {@code false} otherwise
     */
    public boolean isInRange(int x, int y) {
        if (isPieceInBoard(x, y)) return true;
        System.out.println("Out of range! Try again.");
        return false;
    }

    /**
     * Return a {@code boolean} value to datermind is the {@code piece (x, y)}
     * be able to place on board . The condition will verifly
     * {@code board[x][y]} is empty.
     * 
     * In addition, console will print the err message if the condition is {@code false}.
     * @param x {@code row} of the piece
     * @param y {@code col} of the piece
     * @return  {@code true} if the move is valid, {@code false} otherwise
     */
    public boolean isVaild(int x, int y) {
        if (board[x][y] == 0) return true;
        System.out.println("Invalid move. Try again.");
        return false;
    }

    /**
     * Place a piece on board in the index of {@code (x, y)} on board .
     * @param player    who place the piece
     * @param x         {@code row} of the piece
     * @param y         {@code col} of the piece
     */
    public void placePiece(int player, int x, int y) {
        board[x][y] = player;
    }

    /**
     * Return a {@code boolean} value to datermind is the game already end.
     * The method will check the one of {@code piece (x, y)} of different
     * postion is already achieve the win condition.
     * @param x {@code row} of the piece
     * @param y {@code col} of the piece
     * @return  {@code true} if the game has ended, {@code false} otherwise
     */
    public boolean isEnd(int x, int y) {
        return isAdjacentPiecesSame(x, y, 1, 0) // Horizontal
            || isAdjacentPiecesSame(x, y, 0, 1) // Vertical
            || isAdjacentPiecesSame(x, y, 1, 1) // Diagonal \
            || isAdjacentPiecesSame(x, y, -1, 1);       // Diagonal /
    }

    /**
     * Return a {@code boolean} value to datermind is the direction {@code x}
     * and {@code y} of {@code piece (x, y)} is bigger than win condition.
     * 
     * @param x     {@code row} of the piece
     * @param y     {@code col} of the piece
     * @param dir_x direction along the {@code x-axis}
     * @param dir_y direction along the {@code y-axis}
     * @return      {@code true} if the winning condition is met, {@code false} otherwise
     */
    public boolean isAdjacentPiecesSame(int x, int y, int dir_x, int dir_y) {

        /* self piece */
        int noOfPieces = 1;

        /* adjacent pieces */
        for (int i = 0;
                noOfPieces < IN_A_ROW
             && isPieceInBoard(x + (i + 1) * dir_x, y + (i + 1) * dir_y)
             && board[x + i * dir_x][y + i * dir_y] == board[x + (i + 1) * dir_x][y + (i + 1) * dir_y];
            ++i) ++noOfPieces;

        for (int i = 0;
                noOfPieces < IN_A_ROW
             && isPieceInBoard(x - (i + 1) * dir_x, y - (i + 1) * dir_y)
             && board[x - i * dir_x][y - i * dir_y] == board[x - (i + 1) * dir_x][y - (i + 1) * dir_y];
            ++i) ++noOfPieces;
        
        return noOfPieces >= IN_A_ROW;
    }

    /**
     * Return a {@code boolean} value to datermind the {@code piece (x, y)}
     * in range of board .
     * @param x {@code row} of the piece
     * @param y {@code col} of the piece
     * @return  {@code true} if the piece is on the board, {@code false} otherwise
     */
    public boolean isPieceInBoard(int x, int y) {
        return x >= 0 && x < BOARD_LENGTH && y >= 0 && y < BOARD_LENGTH;
    }

    /**
     * Clears the console output.
     */
    public static void clearPrint() {
        System.out.print("\033[H\033[2J");
    }
    public static void main(String[] args) {
        clearPrint();
        Gomoku myGomokuGame = new Gomoku();
        myGomokuGame.startGame();
    }
}
