
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
 *
 * Adam Browning
 * 1/3/2020
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
public class Battleship
{
    public static void main() {

        String[][] P1Shipboard = new String[10][10];                    // Rows, Column
        String[][] P2Shipboard = new String[10][10];
        String[][] P1Torpedoboard = new String[10][10];
        String[][] P2Torpedoboard = new String[10][10];

        Start(P1Shipboard, P2Shipboard, P1Torpedoboard, P2Torpedoboard);

        placeShips(P1Shipboard, 1, "ShipBoard");

    }

    private static void Start(String[][] P1Shipboard, String[][] P2Shipboard, String[][] P1Torpedoboard, String[][] P2Torpedoboard) {
        for (int r = 0; r < P1Shipboard.length; r++) {
            for (int c = 0; c < P1Shipboard[0].length; c++) {
                P1Shipboard[r][c] = "-";              
                P2Shipboard[r][c] = "b";              
                P1Torpedoboard[r][c] = "c";              
                P2Torpedoboard[r][c] = "d";              
            }
        }        
    }

    private static void printP1Shipboard(String[][] input, int playerNumber, String boardType) {
        if (playerNumber == 1 && boardType.equalsIgnoreCase("ShipBoard")) {
            System.out.println("\f\n                           𝙋𝙡𝙖𝙮𝙚𝙧 𝟏'𝙨 𝙎𝙝𝙞𝙥 𝘽𝙤𝙖𝙧𝙙\n"); 
        } else if (playerNumber == 2 && boardType.equalsIgnoreCase("ShipBoard")) {
            System.out.println("\f\n                           𝙋𝙡𝙖𝙮𝙚𝙧 𝟐'𝙨 𝙎𝙝𝙞𝙥 𝘽𝙤𝙖𝙧𝙙\n"); 
        } else if (playerNumber == 1 && boardType.equalsIgnoreCase("TorpedoBoard")) {
            System.out.println("\f\n                         𝙋𝙡𝙖𝙮𝙚𝙧 𝟏'𝙨 𝙏𝙤𝙧𝙥𝙚𝙙𝙤 𝘽𝙤𝙖𝙧𝙙\n"); 
        } else if (playerNumber == 2 && boardType.equalsIgnoreCase("TorpedoBoard")) {
            System.out.println("\f\n                         𝙋𝙡𝙖𝙮𝙚𝙧 𝟐'𝙨 𝙏𝙤𝙧𝙥𝙚𝙙𝙤 𝘽𝙤𝙖𝙧𝙙\n"); 
        } else {
            System.out.println("\f\n                             𝙉𝙤 𝘽𝙤𝙖𝙧𝙙 𝙉𝙖𝙢𝙚\n"); 
        }
        System.out.print("  ");
        for (int r = 0; r < input.length; r++) {   
            System.out.print("    " + (String.valueOf((char)(r+65))) + "  ");
        }             
        for (int r = 0; r < input.length; r++) {   
            for (int c = 0; c < input[0].length; c++) { 
                if (c%10 == 0) {
                    System.out.print("\n\n"); 
                    if (r == 9) {
                        System.out.print((r+1) + " ");
                    } else {
                        System.out.print((r+1) + "  ");
                    }
                }
                System.out.print("   " + input[r][c] + "   ");
            }   
        }
    }

    private static void placeShips(String[][] input, int playerNumber, String boardType) {
        Scanner scanner = new Scanner(System.in);
        boolean selectionComplete = false;
        int shipLength = 4;

        Pattern pattern1 = Pattern.compile("^([B|C|D|b|c|d]$)");

        printP1Shipboard(input, playerNumber, boardType);
        System.out.print("\n\n\n\nChoose which ship to place: Cruiser, Destroyer, Battleship [C, D, B] ");

        while (selectionComplete == false) {
            Matcher matcher = pattern1.matcher(scanner.nextLine());
            if (matcher.matches()) {               
                String shipType = matcher.group(1).toUpperCase();
                if (shipType.equalsIgnoreCase("B")) {
                    shipLength = 5;
                }
                chooseLocation(input, shipLength, shipType, playerNumber, boardType);
                selectionComplete = true;
            } else {
                printP1Shipboard(input, playerNumber, boardType);
                System.out.print("\n\nError - Invalid ship type\n\nChoose which ship to place: Cruiser, Destroyer, Battleship [C, D, B] ");
            }
        }
    }

    private static void chooseLocation(String[][] input, int shipLength, String shipType, int playerNumber, String boardType) {
        Scanner scanner = new Scanner(System.in);
        boolean selectionComplete = false;

        Pattern pattern1 = Pattern.compile("^([A-Ja-j])([1-9]|10)$");

        printP1Shipboard(input, playerNumber, boardType);
        System.out.print("\n\n\n\nShip length: " + shipLength + ". Choose a starting location for your ship [Ex. A1] ");

        while (selectionComplete == false) {
            Matcher matcher = pattern1.matcher(scanner.nextLine());
            if (matcher.matches()) {               
                int row = Integer.parseInt(matcher.group(2));
                int column = (((int)matcher.group(1).toUpperCase().charAt(0)) - 64);

                chooseDirection(input, shipLength, row, column, shipType, playerNumber, boardType);
                selectionComplete = true;
            } else {
                printP1Shipboard(input, playerNumber, boardType);
                System.out.print("\n\nError - Invalid coordinate\n\nShip length: " + shipLength + ". Choose a starting location for your ship [Ex. A1] ");
            }
        }        
    }

    private static void chooseDirection(String[][] input, int shipLength, int row, int column, String shipType, int playerNumber, String boardType) {
        Scanner scanner = new Scanner(System.in);
        boolean selectionComplete = false;

        Pattern pattern1 = Pattern.compile("^([U|D|L|R|u|d|l|r]$)");

        input[row-1][column-1] = shipType;
        printP1Shipboard(input, playerNumber, boardType);
        System.out.print("\n\n\n\nShip length: " + shipLength + ". Choose direction the ship will go from this point: Up, Down, Left, Right [U, D, L, R] ");

        while (selectionComplete == false) {
            Matcher matcher = pattern1.matcher(scanner.nextLine());
            if (matcher.matches()) {               
                String direction = matcher.group(1).toUpperCase();
                finalizeShipPlacement(input, shipLength, row, column, shipType, direction, playerNumber, boardType);
                selectionComplete = true;
            } else {
                printP1Shipboard(input, playerNumber, boardType);
                System.out.print("\n\nError - Invalid direction\n\nShip length: " + shipLength + ". Choose direction the ship will go from this point: Up, Down, Left, Right [U, D, L, R] ");
            }
        }        
    }

    private static void finalizeShipPlacement(String[][] input, int shipLength, int row, int column, String shipType, String direction, int playerNumber, String boardType) {
        boolean cannotPlace = true;

        printP1Shipboard(input, playerNumber, boardType);

        while (cannotPlace == true) {
            if (direction.equalsIgnoreCase("U")) {
                if (row >= 4) {
                    for (int i = 0; i < shipLength - 1; i++) {
                        input[(row - 1) - (i + 1)][column - 1] = shipType;
                        cannotPlace = false;
                    } 
                    printP1Shipboard(input, playerNumber, boardType);
                } else {
                    System.out.print("Cannot fit");
                    chooseDirection(input, shipLength, row, column, shipType, playerNumber, boardType);
                } 
            }
        }
    }
}

