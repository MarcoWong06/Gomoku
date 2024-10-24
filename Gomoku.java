import java.util.Scanner;

/**
 * Copyright (c) 2024, WONG Sai Lung. All rights reserved.
 * @author WONG, Sai Lung
 * @version v1.0.0
 */
public class Gomoku {
    public static Scanner sc = new Scanner(System.in);

    public int bLen;        // board length
    public int inARow;      // number in a row to win
    public int[][] board;   // board object

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

        /* Initialization */
        int x = 0, y = 0, player = 2;
        printBoard();

        /* Game-loop */
        do {
            player = 3 - player;    // Switch player
            do {                    // A scanner input do-while-loop
                printInfo(player);
                x = sc.nextInt();
                y = sc.nextInt();
            } while (!isInRange(x, y) || !isVaild(x, y));
            placePiece(player, x, y);
            // clearPrint();
            printBoard();
        } while (!isEnd(x, y));

        /* Game end */
        System.out.println("Player " + player + " wins!");
    }

    /**
     * In game-loop, board {@code object} will print out at console.
     */
    public void printBoard() {
        System.out.println();

        /* Upper part of board */
        for (int i = 0; i < bLen; i++) {

            /* row index of board */
            System.out.print(i + (i < 10 ? " |" : "|"));

            /* board piece */
            for (int j : board[i]) System.out.print(" " + j);

            System.out.println();
        }

        /* boundary and column index */
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
     * @param player who's turn
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
     * Return a {@code boolean} value to datermind is the {@code piece (x, y)}
     * be able to place on board {@code object}. The condition will verifly
     * {@code board[x][y]} is empty.
     * 
     * In addition, console will print the err message if the condition is {@code false}.
     * @param x {@code row} of the piece
     * @param y {@code col} of the piece
     * @return  a {@code boolean} value to datermind is the {@code piece (x, y)}
     *          be able to place on board {@code object}
     */
    public boolean isVaild(int x, int y) {
        if (board[x][y] == 0) return true;
        System.out.println("Invalid move. Try again.");
        return false;
    }

    /**
     * Place a piece on board {@code odject} in the index of {@code (x, y)} on board {@code object}.
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
     * @return  a {@code boolean} value to datermind is the game already end
     */
    public boolean isEnd(int x, int y) {
        return 
            isAdjacentPiecesSame(x, y, 1, 0) ||
            isAdjacentPiecesSame(x, y, 0, 1) ||
            isAdjacentPiecesSame(x, y, 1, 1) ||
            isAdjacentPiecesSame(x, y, -1, 1);
    }

    /**
     * Return a {@code boolean} value to datermind is the direction {@code x}
     * and {@code y} of {@code piece (x, y)} is bigger than win condition.
     * 
     * The case of {@code dir_x, dir_y} are
     * 
     * {@code (1, 0)}: Horizontally;
     * {@code (0, 1)}: Vertically;
     * {@code (1, 1)}: Top left to bottem right;
     * {@code (-1, 1)}: Bottem right to top left.
     * 
     * @param x     {@code row} of the piece
     * @param y     {@code col} of the piece
     * @param dir_x direction of {@code x}
     * @param dir_y direction of {@code y}
     * @return      a {@code boolean} value to diatermind is the direction {@code x}
     *              and {@code y} of {@code piece (x, y)} is bigger than win condition.
     */
    public boolean isAdjacentPiecesSame(int x, int y, int dir_x, int dir_y) {

        /* self piece */
        int noOfPieces = 1;

        /* adjacent pieces */
        for (int i = 0; noOfPieces < inARow && isPieceInBoard(x + (i + 1) * dir_x, y + (i + 1) * dir_y) && board[x + i * dir_x][y + i * dir_y] == board[x + (i + 1) * dir_x][y + (i + 1) * dir_y]; ++i) ++noOfPieces;
        for (int i = 0; noOfPieces < inARow && isPieceInBoard(x - (i + 1) * dir_x, y - (i + 1) * dir_y) && board[x - i * dir_x][y - i * dir_y] == board[x - (i + 1) * dir_x][y - (i + 1) * dir_y]; ++i) ++noOfPieces;
        
        return noOfPieces >= inARow;
    }

    /**
     * Return a {@code boolean} value to datermind the {@code piece (x, y)}
     * in range of board {@code object}.
     * @param x {@code row} of the piece
     * @param y {@code col} of the piece
     * @return  a {@code boolean} value to datermind the {@code piece (x, y)}
     *          in range of board {@code object}.
     */
    public boolean isPieceInBoard(int x, int y) {
        return x >= 0 && x < bLen && y >= 0 && y < bLen;
    }

    /**
     * Clear the console output.
     */
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