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

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
public class BattleshipRemade
{
    public static int coordinateX;
    public static int coordinateY;
    public static int coordinateXShot;
    public static int coordinateYShot;
    public static String placementDirection;
    public static int[][] hitCount = new int[2][3];
    public static int[][] shipsSunk = new int[2][3];
    public static String[][][][] board = new String[2][2][10][10];          // Player Number, Board Type, Rows, Columns
    public static int currentPlayer = 1;
    public static int counterP1 = 0;    
    public static int counterP2 = 0;    

    public static void main() {
        fillAllBoards("-");

        System.out.print("Player 1 Is Placing Ships...");
        waitTime(1500);

        printBoard(1, 1);
        System.out.print("\n\n");
        placeShips(1, 1, "c");                                   // c = cruiser, d = destroyer, b = battleship
        placeShips(1, 1, "d");
        placeShips(1, 1, "b");
        waitTime(500);
        System.out.print("\fPlayer 2 Is Placing Ships...");
        waitTime(1500);

        printBoard(2, 1);
        System.out.print("\n\n");
        placeShips(2, 1, "c");                                   // c = cruiser, d = destroyer, b = battleship
        placeShips(2, 1, "d");
        placeShips(2, 1, "b");
        waitTime(500);

        System.out.print("\fStarting Game..."); 
        waitTime(2000);                                                   // starting game

        doTurns();

    }

    private static void fillAllBoards(String fillCharacter) {             // Fills all 4 boards
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

    private static void fillBoard(int playerNumber, int boardType, String fillCharacter) {            // Fills a specific board
        for (int r = 0; r < board[0][0].length; r++) {
            for (int c = 0; c < board[0][0][0].length; c++) {       
                board[playerNumber-1][boardType-1][r][c] = fillCharacter;                       
            }
        }          
    }

    private static void printBoard(int playerNumber, int boardType) {             // Prints board based on the player number and board type
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

    private static void printDuelBoard(int playerNumber) {            // Print side by side board
        if (playerNumber == 1) {
            System.out.println("\f\n                           𝙋𝙡𝙖𝙮𝙚𝙧 𝟏'𝙨 𝙎𝙝𝙞𝙥 𝘽𝙤𝙖𝙧𝙙\t\t\t\t\t\t\t\t      𝙋𝙡𝙖𝙮𝙚𝙧 𝟏'𝙨 𝙏𝙤𝙧𝙥𝙚𝙙𝙤 𝘽𝙤𝙖𝙧𝙙\n"); 
            System.out.print("  ");
        }
        if (playerNumber == 2) {
            System.out.println("\f\n                           𝙋𝙡𝙖𝙮𝙚𝙧 𝟐'𝙨 𝙎𝙝𝙞𝙥 𝘽𝙤𝙖𝙧𝙙\t\t\t\t\t\t\t\t      𝙋𝙡𝙖𝙮𝙚𝙧 𝟐'𝙨 𝙏𝙤𝙧𝙥𝙚𝙙𝙤 𝘽𝙤𝙖𝙧𝙙\n"); 
            System.out.print("  ");
        }

        for (int r = 0; r < board[0][0].length; r++) {   
            System.out.print("    " + (String.valueOf((char)(r+65))) + "  ");
        }   
        System.out.print("\t     ");
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
                System.out.print("   " + board[playerNumber-1][0][r][c] + "   ");
            }  
            System.out.print("          ");
            for (int i = 0; i < board[0][0][0].length; i++) {
                if (i%10 == 0) {
                    if (r == 9) {
                        System.out.print((r+1) + " ");
                    } else {
                        System.out.print((r+1) + "  ");
                    }                            
                }
                System.out.print("   " + board[playerNumber-1][1][r][i] + "   ");;
            }
            if (r == 1) {
                System.out.print("       Symbol Key ");
            }
            if (r == 2) {
                System.out.print("       X = Hit ");
            }
            if (r == 3) {
                System.out.print("       O = Miss ");
            }
            if (r == 4) {
                System.out.print("       C = Cruiser");
            }
            if (r == 5) {
                System.out.print("       D = Cruiser");
            }
            if (r == 6) {
                System.out.print("       B = Battleship");
            }
        }   
    }

    private static void doTurns() {
        Scanner input = new Scanner (System.in);              
        boolean check1 = false;
        boolean check2 = false;

        if (currentPlayer == 1) {
            counterP1++;
        } else if (currentPlayer == 2) {
            counterP2++;
        }

        changeTurns();
        printDuelBoard(currentPlayer);
        System.out.print("\n\n\n\nPlayer " + currentPlayer + " - \nEnter a coordinate to shoot at [Ex A1] ");
        String response = input.nextLine();

        while (check1 == false)
        {
            printDuelBoard(currentPlayer);
            if (checkShotLocation(response)) {                   // checks if location is good                                      
                check1 = true;
                if (isHit(currentPlayer)) {              // check if hit or miss
                    board[currentPlayer-1][1][(coordinateYShot - 1)][(coordinateXShot - 1)] = "X";
                    printDuelBoard(currentPlayer);                    
                    System.out.print("\n\nHit!");
                    checkForSink();
                    if (checkForWin()) {
                        System.exit(0);
                    }
                    waitTime(1000);  

                } else {
                    board[currentPlayer-1][1][(coordinateYShot - 1)][(coordinateXShot - 1)] = "O";
                    printDuelBoard(currentPlayer);
                    System.out.print("\n\nMiss!");    
                    waitTime(1000);  
                }

                if (currentPlayer == 1) {                           // changes players
                    currentPlayer = 2;
                } else if (currentPlayer == 2) {
                    currentPlayer = 1;
                }

                System.out.print("\n\nAre you done with your turn [Y] ");
                String yesOrNo = input.nextLine();

                while (check2 == false)
                {
                    if (checkYesOrNo(yesOrNo)) {
                        check2 = true;
                        doTurns();
                    } else {
                        printDuelBoard(currentPlayer);     
                        System.out.print("\n\nError - The only option is 'Y'\n\nAre you done with your turn [Y] ");
                        yesOrNo = input.nextLine();
                    }
                }

                doTurns();
            } else {
                System.out.print("\n\nError - Invalid Coordinate\n\nPlayer " + currentPlayer + " - \nEnter a coordinate to shoot at [Ex A1] ");
                response = input.nextLine();
            }
        }
    }

    private static boolean checkYesOrNo(String yesOrNo) {
        Pattern pattern1 = Pattern.compile("^([Y|y]$)");
        Matcher matcher = pattern1.matcher(yesOrNo);
        boolean goodResponse = false;     

        if (matcher.matches()) {               
            goodResponse = true;           
        } else {
            goodResponse = false;
        }

        return goodResponse;
    }

    private static boolean checkForSink() {
        boolean sink = false;

        int enemyPlayer = 0;
        if (currentPlayer == 1) {
            enemyPlayer = 2;
        }
        if (currentPlayer == 2) {
            enemyPlayer = 1;
        } 

        if (shipsSunk[currentPlayer-1][0] != 1) {
            if (hitCount[currentPlayer-1][0] == 4) {
                sink = true;
                System.out.print("\nYou have sunk player " + enemyPlayer + "'s crusier!");
                shipsSunk[currentPlayer-1][0] = 1;
            }
        }

        if (shipsSunk[currentPlayer-1][1] != 1) {
            if (hitCount[currentPlayer-1][1] == 4) {
                sink = true;
                System.out.print("\nYou have sunk player " + enemyPlayer + "'s destroyer!");
                shipsSunk[currentPlayer-1][1] = 1;
            }
        }

        if (shipsSunk[currentPlayer-1][2] != 1) {
            if (hitCount[currentPlayer-1][2] == 5) {
                sink = true;
                System.out.print("\nYou have sunk player " + enemyPlayer + "'s battelship!");
                shipsSunk[currentPlayer-1][2] = 1;
            }
        }
        return sink;
    }

    private static boolean checkForWin() {
        boolean winState = false;
        int turnCounter = 0;

        if (currentPlayer == 1) {
            turnCounter = counterP1;
        } else if (currentPlayer == 2) {
            turnCounter = counterP2;
        }

        if (hitCount[currentPlayer-1][0] == 4 && hitCount[currentPlayer-1][1] == 4 && hitCount[currentPlayer-1][2] == 5) {
            winState = true;
            System.out.print("\n\nPlayer " + currentPlayer + " has won the game in " + turnCounter + " turns!");
        }

        return winState;
    }

    private static void changeTurns() {
        if (currentPlayer == 1) {
            System.out.print("\fPlayer 1's Turn...");
            waitTime(2000);  
        } 
        if (currentPlayer == 2) {
            System.out.print("\fPlayer 2's Turn...");
            waitTime(2000);  
        } 
    }

    private static boolean checkShotLocation(String response) {
        Pattern pattern1 = Pattern.compile("^([A-Ja-j])([1-9]|10)$");
        Matcher matcher = pattern1.matcher(response);
        boolean goodResponse = false; 

        if (matcher.matches()) {               
            coordinateYShot = Integer.parseInt(matcher.group(2));
            coordinateXShot = (((int)matcher.group(1).toUpperCase().charAt(0)) - 64);
            if (board[currentPlayer-1][1][(coordinateYShot - 1)][(coordinateXShot - 1)].equalsIgnoreCase("-")) {

                goodResponse = true;
            } else {
                goodResponse = false;
            }
        }
        return goodResponse;
    }

    private static boolean isHit(int currentPlayer) {
        boolean isHit = false;
        int enemyPlayer = 0;

        if (currentPlayer == 1) {
            enemyPlayer = 1;
        }
        if (currentPlayer == 2) {
            enemyPlayer = 0;
        } 

        if (!(board[enemyPlayer][0][(coordinateYShot - 1)][(coordinateXShot - 1)].equalsIgnoreCase("-"))) {
            isHit = true;
        }

        if (isHit) {

            if (board[enemyPlayer][0][(coordinateYShot - 1)][(coordinateXShot - 1)].equalsIgnoreCase("c")) {
                hitCount[currentPlayer-1][0]++;
            }
            if (board[enemyPlayer][0][(coordinateYShot - 1)][(coordinateXShot - 1)].equalsIgnoreCase("d")) {
                hitCount[currentPlayer-1][1]++;
            }
            if (board[enemyPlayer][0][(coordinateYShot - 1)][(coordinateXShot - 1)].equalsIgnoreCase("b")) {
                hitCount[currentPlayer-1][2]++;
            }
        }

        return isHit;
    }

    private static void placeShips(int playerNumber, int boardType, String shipType) {       // 1 = cruiser, 2 = destroyer, 3 = battleship
        Scanner input = new Scanner (System.in);        

        int shipLength = 4;
        String shipName = "";
        String response = "";
        boolean check1 = false;
        boolean check2 = false;
        boolean check3 = false;

        shipType = shipType.toUpperCase();

        if (shipType.equalsIgnoreCase("c")) {
            shipName = "Crusier";
        }
        if (shipType.equalsIgnoreCase("d")) {
            shipName = "Destroyer";
        }
        if (shipType.equalsIgnoreCase("b")) {
            shipLength = 5;
            shipName = "Battle Ship";
        }

        System.out.print("\n\nPlayer " + playerNumber + " - \nShip Type: " + shipName + "\nShip Length: " + shipLength + "\nChoose a starting location for your ship [Ex. A1]");
        response = input.nextLine();

        while (check1 == false) {           
            printBoard(playerNumber, boardType);
            if (checkStartingLocation(playerNumber, boardType, shipType, shipName, response, shipLength)) {
                check1 = true;
                System.out.print("\n\n\n\nPlayer " + playerNumber + " - \nShip Type: " + shipName + "\nShip Length: " + shipLength + "\nChoose a direction to move your ship from this point: Up, Right, Left, Down [U, R, L, D]");
                response = input.nextLine();

                while (check2 == false) {
                    printBoard(playerNumber, boardType);
                    if (checkDirection(playerNumber, boardType, shipType, shipName, response, shipLength)) {                        
                        if (placeShipOnBoard(playerNumber, boardType, shipLength, shipType, shipName)) {
                            check2 = true;
                        } else {
                            System.out.print("\n\nError - Cannot Place Ship There\n\nPlayer " + playerNumber + " - \nWould you like to:\na) change the coordinates \nb) change the direction");
                            response = input.nextLine();
                            check2 = false;
                            if (checkAOrB(response)){
                                if (response.equalsIgnoreCase("a")) {
                                    check2 = true;                                
                                    printBoard(playerNumber, boardType);
                                    System.out.print("\n\n");
                                    placeShips(playerNumber, boardType, shipType);
                                } else {
                                    check2 = false;       
                                    check3 = true;
                                }
                            } else {
                                check
                            }   
                        }
                    } else {
                        if (check3 == true){
                            System.out.print("\n\n\n\nPlayer " + playerNumber + " - \nShip Type: " + shipName + "\nShip Length: " 
                                + shipLength + "\nChoose a direction to move your ship from this point: Up, Right, Left, Down [U, R, L, D]");
                            check3 = false;
                        } else if (check3 == false) {
                            System.out.print("\n\nError - Invalid Direction\n\nPlayer " + playerNumber + " - \nShip Type: " + shipName + "\nShip Length: " 
                                + shipLength + "\nChoose a direction to move your ship from this point: Up, Right, Left, Down [U, R, L, D]");
                        }
                        response = input.nextLine();
                    }
                }
            } else {
                System.out.print("\n\nError - Invalid Coordinate\n\nPlayer " + playerNumber + " - \nShip Type: " + shipName + "\nShip Length: " + shipLength + "\nChoose a starting location for your ship [Ex. A1]");
                response = input.nextLine();
            }
        }
    }   

    private static boolean checkAOrB (String response) {
        Pattern pattern1 = Pattern.compile("^([A|a|B|b]$)");
        Matcher matcher = pattern1.matcher(response);
        boolean goodResponse = false;     

        if (matcher.matches()) {               
            goodResponse = true;           
        } else {
            goodResponse = false;
        }

        return goodResponse;
    }

    private static boolean checkStartingLocation(int playerNumber, int boardType, String shipType, String shipName, String response, int shipLength) {        // Checks if starting location input and avalibility ok   
        Pattern pattern1 = Pattern.compile("^([A-Ja-j])([1-9]|10)$");
        Matcher matcher = pattern1.matcher(response);
        boolean goodResponse = false;       

        if (matcher.matches()) {               
            coordinateY = Integer.parseInt(matcher.group(2));
            coordinateX = (((int)matcher.group(1).toUpperCase().charAt(0)) - 64);

            if (!((board[playerNumber-1][boardType-1][(coordinateY - 1)][(coordinateX - 1)].equalsIgnoreCase("-")))) {
                goodResponse = false;
            } else {
                printBoard(playerNumber, boardType);
                goodResponse = true;
            }
        } else {
            goodResponse = false;
        }
        return goodResponse;
    }

    private static boolean checkDirection(int playerNumber, int boardType, String shipType, String shipName, String response, int shipLength) {           // Check if direction input is ok
        Pattern pattern1 = Pattern.compile("^([U|D|L|R|u|d|l|r]$)");
        Matcher matcher = pattern1.matcher(response);
        boolean goodResponse = false;     

        if (matcher.matches()) {               
            placementDirection = matcher.group(1).toUpperCase();
            goodResponse = true;           
        } else {
            goodResponse = false;
        }

        return goodResponse;
    }

    private static boolean placeShipOnBoard(int playerNumber, int boardType, int shipLength, String shipType, String shipName) {              // check if ship fits at location
        int counter = 0;
        shipName = shipName.toLowerCase();
        String coordinateXLetter = Character.toString((char)(coordinateX + 64));

        if (placementDirection.equalsIgnoreCase("U")) {
            for (int i = 0; i < shipLength; i++) {
                if (coordinateY - 1 - i < 0) {
                    counter++;
                } else if (!(board[playerNumber-1][boardType-1][(coordinateY - 1) - i][(coordinateX - 1)].equalsIgnoreCase("-"))) {
                    counter++;
                }
            } 
            if (counter == 0) {
                for (int i = 0; i < shipLength; i++) {
                    board[playerNumber-1][boardType-1][(coordinateY - 1) - i][(coordinateX - 1)] = shipType;
                    waitTime(120); 
                    printBoard(playerNumber, boardType);
                }
                if (!(shipType.equalsIgnoreCase("B"))){
                    System.out.print("\n\nPlaced the " + shipName + " at coordinate " + coordinateXLetter + "" + coordinateY + " going upwards");
                }
                return true;
            } 
        }
        if (placementDirection.equalsIgnoreCase("D")) {
            for (int i = 0; i < shipLength; i++) {
                if (coordinateY - 1 + i > 9) {
                    counter++;
                } else if (!(board[playerNumber-1][boardType-1][(coordinateY - 1) + i][(coordinateX - 1)].equalsIgnoreCase("-"))) {
                    counter++;
                }
            } 
            if (counter == 0) {
                for (int i = 0; i < shipLength; i++) {
                    board[playerNumber-1][boardType-1][(coordinateY - 1) + i][(coordinateX - 1)] = shipType;
                    waitTime(120); 
                    printBoard(playerNumber, boardType);
                }
                if (!(shipType.equalsIgnoreCase("B"))){
                    System.out.print("\n\nPlaced the " + shipName + " at coordinate " + coordinateXLetter + "" + coordinateY + " going downwards");
                }
                return true;
            } 
        }
        if (placementDirection.equalsIgnoreCase("L")) {
            for (int i = 0; i < shipLength; i++) {
                if (coordinateX - 1 - i < 0) {
                    counter++;
                } else if (!(board[playerNumber-1][boardType-1][(coordinateY - 1)][(coordinateX - 1) - i].equalsIgnoreCase("-"))) {
                    counter++;
                }
            } 
            if (counter == 0) {
                for (int i = 0; i < shipLength; i++) {
                    board[playerNumber-1][boardType-1][(coordinateY - 1)][(coordinateX - 1) - i] = shipType;
                    waitTime(120); 
                    printBoard(playerNumber, boardType);
                }
                if (!(shipType.equalsIgnoreCase("B"))){
                    System.out.print("\n\nPlaced the " + shipName + " at coordinate " +  coordinateXLetter + "" + coordinateY + " going left");
                }
                return true;
            } 
        }
        if (placementDirection.equalsIgnoreCase("R")) {
            for (int i = 0; i < shipLength; i++) {
                if (coordinateX - 1 + i > 9) {
                    counter++;
                } else if (!(board[playerNumber-1][boardType-1][(coordinateY - 1)][(coordinateX - 1) + i].equalsIgnoreCase("-"))) {
                    counter++;
                }
            } 
            if (counter == 0) {
                for (int i = 0; i < shipLength; i++) {
                    board[playerNumber-1][boardType-1][(coordinateY - 1)][(coordinateX - 1) + i] = shipType;
                    waitTime(120); 
                    printBoard(playerNumber, boardType);
                }
                if (!(shipType.equalsIgnoreCase("B"))) {
                    System.out.print("\n\nPlaced the " + shipName + " at coordinate " +  coordinateXLetter + "" + coordinateY + " going right");
                }
                return true;
            } 
        }
        return false;
    }

    private static void waitTime(int time) {            // wait time for aesthetics
        try {
            Thread.sleep(time);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }   
    }
}

