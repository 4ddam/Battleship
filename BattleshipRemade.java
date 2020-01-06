/*
 * Before starting the assignment, read through the entire set of directions. This project will take several class periods (approximately 10 hrs.) 
 * so make a plan, don't wait til the last few days and expect to complete this project. 
 * Please use your time wisely. You MUST plan your code using some type of Document creator like Google 
 * Docs (not the code but the pseudocode). I encourage cooperation, a good plan makes writing code easier. 
 * If asked I can help you get your plan started as well as give you ideas.
 * Plan all of your for loops, while loops, if-and-or statements, array declarations, etc…..
 * 
 * 1. Create four 10-by-10 game boards – a “Ship Board” for each player (to place ships on), and a 
 * “Torpedo Board” for each player (to fire torpedoes on). Place a dash (-) in each “cell” of each board.
 * 
 * 2. Start by asking each player to place his/her 3 ships:
 * a. First, display his/her Ship Board.
 * b. The Cruiser and the Destroyer are both 4 cells long; the Battleship is 5.
 * c. You must ask the player where to start placing a ship (for example, Row 3, Column 7),
 * and then asking which direction the ship should go from there (up, down, left, or right).
 * (this will require some serious(SERIOUS) error checking!)
 * d. Assign an uppercase C (Cruiser), D (Destroyer), or B (Battleship) to the cells where a
 * ship is located. (you can be creative here)
 * 
 * 3. Now…let the game begin. Display Player 1’s Ship Board and Torpedo Board. 
 * Let Player 1 enter the coordinates of the location he would like to fire a torpedo at. 
 * Announce whether he hits or misses. If he hits, place an X in the location of the hit 
 * (on Player 1’s Torpedo Board, AND on Player 2’s Ship Board). If he misses, place an uppercase O.
 * 
 * 4. SERIOUS Error checking: do not allow a player to fire at a location he has already fired at; 
 * there are only 10 rows & columns; some directions are impossible.
 * 
 * 5. If a player sinks a ship, announce it. (“You sunk Player 2’s Destroyer!”).
 * 6. After Player 1 fires, it is Player 2’s turn.
 * 7. The players take turns until one player’s ships are all sunk.
 *
 * Adam Browning
 * 1/6/2020
 */

import java.util.Scanner;
public class BattleshipRemade
{
    public static void main() {

        String[][][][] masterBoard = new String[2][2][10][10];              // Player Number, Board Type, Rows, Columns

        fillAllBoards(masterBoard, "-");

        printBoard(masterBoard, 1, 1);

    }

    private static void fillAllBoards(String[][][][] board, String fillCharacter) {             // Fills all 4 boards
        for (int p = 0; p < board.length; p++) {
            for (int b = 0; b < board[0].length; b++) {
                for (int r = 0; r < board[0][0].length; r++) {
                    for (int c = 0; c < board[0][0][0].length; c++) {       
                        board[p][b][r][c] = fillCharacter;                       
                    }
                }          
            }
        }
    }

    private static void fillBoard(String[][][][] board, int playerNumber, int boardType, String fillCharacter) {            // Fills a specific board
        for (int r = 0; r < board[0][0].length; r++) {
            for (int c = 0; c < board[0][0][0].length; c++) {       
                board[playerNumber-1][boardType-1][r][c] = fillCharacter;                       
            }
        }          
    }

    private static void printBoard(String[][][][] board, int playerNumber, int boardType) {             // Prints board based on the player number and board type
        if (playerNumber == 1 && boardType == 1) {
            System.out.println("\f\n                           𝙋𝙡𝙖𝙮𝙚𝙧 𝟏'𝙨 𝙎𝙝𝙞𝙥 𝘽𝙤𝙖𝙧𝙙\n"); 
        } else if (playerNumber == 2 && boardType == 1) {
            System.out.println("\f\n                           𝙋𝙡𝙖𝙮𝙚𝙧 𝟐'𝙨 𝙎𝙝𝙞𝙥 𝘽𝙤𝙖𝙧𝙙\n"); 
        } else if (playerNumber == 1 && boardType == 2) {
            System.out.println("\f\n                         𝙋𝙡𝙖𝙮𝙚𝙧 𝟏'𝙨 𝙏𝙤𝙧𝙥𝙚𝙙𝙤 𝘽𝙤𝙖𝙧𝙙\n"); 
        } else if (playerNumber == 2 && boardType == 2) {
            System.out.println("\f\n                         𝙋𝙡𝙖𝙮𝙚𝙧 𝟐'𝙨 𝙏𝙤𝙧𝙥𝙚𝙙𝙤 𝘽𝙤𝙖𝙧𝙙\n"); 
        } else {
            System.out.println("\f\n                             𝙉𝙤 𝘽𝙤𝙖𝙧𝙙 𝙉𝙖𝙢𝙚\n"); 
        }
        System.out.print("  ");
        for (int r = 0; r < board[0][0].length; r++) {   
            System.out.print("    " + (String.valueOf((char)(r+65))) + "  ");
        }             
        for (int r = 0; r < board[0][0].length; r++) {
            System.out.print("\n\n"); 
            for (int c = 0; c < board[0][0][0].length; c++) { 
                if (c%10 == 0) {
                    if (r == 9) {
                        System.out.print((r+1) + " ");
                    } else {
                        System.out.print((r+1) + "  ");
                    }    
                }
                System.out.print("   " + board[playerNumber-1][boardType-1][r][c] + "   ");
            }   
            if (r == 3) {
                System.out.print("       Symbol Key ");
            }
            if (r == 4) {
                System.out.print("       X = Hit ");
            }
            if (r == 5) {
                System.out.print("       O = Miss ");
            }
            if (r == 6) {
                System.out.print("       C = Cruiser    D = Destroyer    B = Battleship  ");
            }
        }

    }
}
