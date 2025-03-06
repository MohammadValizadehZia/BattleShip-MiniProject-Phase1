import java.util.Scanner;

public class BattleShip {
    static final int GRID_SIZE =10;
    static char[][] player1Grid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player2Grid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player1TrackingGrid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player2TrackingGrid = new char[GRID_SIZE][GRID_SIZE];
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){
        initializeGrid(player1Grid);
        initializeGrid(player2Grid);
        initializeGrid(player1TrackingGrid);
        initializeGrid(player2TrackingGrid);
        placeShips(player1Grid);
        placeShips(player2Grid);
        boolean player1Turn = true;
        while (!isGameOver()){
            if (player1Turn){
                System.out.println("blue player turn:");
                printGrid(player1TrackingGrid);
                if (!playerTurn(player2Grid, player1TrackingGrid)){
                    player1Turn = false;
                    continue;
                }
            } else {
                System.out.println("red player turn:");
                printGrid(player2TrackingGrid);
                if (!playerTurn(player1Grid, player2TrackingGrid)){
                    player1Turn = true;
                    continue;
                }
            }
            player1Turn= !player1Turn;
        }
        System.out.println("game Over!");
    }
    static void initializeGrid(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] ='~';
            }
        }
    }
    static void placeShips(char[][] grid) {
        int[] shipSizes = new int[4 ];
        shipSizes[0] = 5; shipSizes[1] = 4; shipSizes[2] = 3; shipSizes[3] = 2;
        for (int size : shipSizes) {
            boolean placed = false;
            while (!placed) {
                int row= (int)(Math.random()*10);
                int col= (int)(Math.random()*10);
                if (col+ size <= 10) {
                    boolean canPlaceRight =true;
                    for (int i =0; i <size;i++) {
                        if (grid[row][col + i] !='~') {
                            canPlaceRight =false;
                            break;
                        }
                    }
                    if (canPlaceRight) {
                        for (int i =0; i< size;i++) {
                            grid[row][col +i] = '+';
                        }
                        placed = true;
                        continue;
                    }
                }
                if (row + size <= 10) {
                    boolean canPlaceDown = true;
                    for (int i = 0; i < size; i++){
                        if (grid[row + i][col] != '~'){
                            canPlaceDown = false;
                            break;
                        }
                    }
                    if (canPlaceDown) {
                        for (int i = 0; i < size; i++){
                            grid[row + i][col] ='+';
                        }
                        placed=true;
                        continue;
                    }
                }
            }
        }
    }
    static boolean canPlaceShip(char[][] grid, int row, int col, int size, boolean horizontal) {
        if (horizontal) {
            if (col + size > GRID_SIZE) return false;
            for (int i = 0; i < size; i++) {
                if (grid[row][col + i] != '~') return false;
            }
        } else {
            if (row + size > GRID_SIZE) return false;
            for (int i = 0; i < size; i++) {
                if (grid[row + i][col] != '~') return false;
            }
        }
        return true;
    }
    static boolean playerTurn(char[][] opponentGrid, char[][] trackingGrid) {
        System.out.println("Enter your attack coordinates: ");
        String input = scanner.nextLine().toUpperCase();
        if (!isValidInput(input)) {
            System.out.println("Invalid input! Skipping your turn.");
            return false;
        }
        char rowChar = input.charAt(1);
        int row = rowChar - '0';
        int col = input.charAt(0) - 'A';
        if (opponentGrid[row][col] == '+'){
            System.out.println("hit!");
            opponentGrid[row][col]='*';
            trackingGrid[row][col] = '*';
        } else {
            System.out.println("miss :)");
            opponentGrid[row][col] ='0';
            trackingGrid[row][col]= '0';
        }
        return true;
    }
    static boolean isGameOver(){
        return allShipsSunk(player1Grid) || allShipsSunk(player2Grid);
    }
    static boolean allShipsSunk(char[][] grid){
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == '+') {
                    return false;
                }
            }
        }
        System.out.println("YOU WON !");
        return true;
    }
    static boolean isValidInput(String input){
        if (input.length() != 2) return false;
        char row = input.charAt(1);
        char col = input.charAt(0);
        if (col < 'A' || col > 'J') return false;
        if (row < '0' || row > '9') return false;
        return true;
    }
    static void printGrid(char[][] grid){
        System.out.print("   ");
        for (int i =0; i< GRID_SIZE;i++) {
            System.out.print((char) ('A'+i)+" ");
        }
        System.out.println();
        for (int i = 0; i < GRID_SIZE; i++){
            System.out.print(i + "  ");
            for (int j = 0; j < GRID_SIZE; j++){
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
