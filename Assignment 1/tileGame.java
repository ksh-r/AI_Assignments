/*Team Daemon Demons*/

/* Environment for Puzzle 8:
Variables:
    state: 2D character array to store the current state
    emptyCoords: array to store current empty space location
                 0th index -> row value of empty space
                 1st index -> column value of empty space
Functions:
    newGame: Creates a random solvable state
    newDepthGame: Creates a random solvable state at specified depth
    getActions: Returns a string of possible moves of the empty space
    move: Moves the empty space to the specified position
    printCurrentState: Prints state array on the console
    checkGoal: Returns true if the current state is the goal state
*/

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class tileGame {

    public char[][] state;
    public int[] emptyCoords;

    public tileGame() {
        state = new char[3][3];
        emptyCoords = new int[2];

        // Creating starting state
        newGame();
        updateEmptyCoords();
    }

    public boolean checkGoal() {
        int flag = 0;
        char ch = '1';
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(state[i][j] != ch)
                    flag++;
                ch++;
            }
        }
        if(flag > 1)
            return false;
        else
            return true;
    }

    public String getActions() {
        String actions = "";
        if(emptyCoords[0] > 0)
            actions += 'U';
        if(emptyCoords[0] < 2)
            actions += 'D';
        if(emptyCoords[1] > 0)
            actions += 'L';
        if(emptyCoords[1] < 2)
            actions += 'R';
        return actions;
    }

    public void move(char ch) {
        if(ch == 'L') {
            state[emptyCoords[0]][emptyCoords[1]] = state[emptyCoords[0]][emptyCoords[1] - 1];
            state[emptyCoords[0]][--emptyCoords[1]] = ' ';
        } else if (ch == 'R') {
            state[emptyCoords[0]][emptyCoords[1]] = state[emptyCoords[0]][emptyCoords[1] + 1];
            state[emptyCoords[0]][++emptyCoords[1]] = ' ';
        } else if(ch == 'U') {
            state[emptyCoords[0]][emptyCoords[1]] = state[emptyCoords[0] - 1][emptyCoords[1]];
            state[--emptyCoords[0]][emptyCoords[1]] = ' ';
        } else {
            state[emptyCoords[0]][emptyCoords[1]] = state[emptyCoords[0] + 1][emptyCoords[1]];
            state[++emptyCoords[0]][emptyCoords[1]] = ' ';
        }
    }

    public void newGame() {
        Integer[] initial = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        // Randomizing the starting position
        List<Integer> initialList = Arrays.asList(initial);
        Collections.shuffle(initialList);
        initialList.toArray(initial);

        // Checking if impossible
        initial = makeSolvable(initial);

        // Filling state array
        int temp = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(initial[temp] == 9) {
                    state[i][j] = ' ';
                } else {
                    state[i][j] = Character.forDigit(initial[temp], 10);
                }
                temp++;
            }
        }
    }

    public void newDepthGame(int d) {
        // Setting state = goal state
        char temp = '1';
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                state[i][j] = temp++;
            }
        }
        state[2][2] = ' ';
        emptyCoords[0] = 2;
        emptyCoords[1] = 2;

        // Making D random moves
        char prevMove = '\0';
        for(int i = 0; i < d; i++) {
            String legalMoves = getActions();
            char currMove = randomMove(legalMoves, prevMove);
            move(currMove);
            prevMove = currMove;
        }
    }

    private char randomMove(String legalMoves, char prevMove) {
        // Removing opposite of previous move, to avoid reaching previous state
        if(prevMove == 'U') {
            legalMoves = legalMoves.replace("D", "");
        } else if(prevMove == 'D'){
            legalMoves = legalMoves.replace("U", "");
        }else if(prevMove == 'L'){
            legalMoves = legalMoves.replace("R", "");
        }else if(prevMove == 'R'){
            legalMoves = legalMoves.replace("L", "");
        }

        // Selecting a random move
        int randomIndex = ThreadLocalRandom.current().nextInt(0, legalMoves.length());
        return legalMoves.charAt(randomIndex);
    }

    private Integer[] makeSolvable(Integer[] arr) {
        int inversion = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = i+1; j < 9; j++) {
                if(arr[i] == 9 || arr[j] == 9)
                    continue;
                if(arr[i] > arr[j])
                    inversion++;
            }
        }
        if(inversion % 2 == 1) {
            inversion = arr[8];
            arr[8] = arr[7];
            arr[7] = inversion;
        }
        return arr;
    }

    public void updateEmptyCoords() {
        int[] coords = {0, 0};
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(state[i][j] == ' ') {
                    coords[0] = i;
                    coords[1] = j;
                }
            }
        }
        emptyCoords = coords;
    }

    public void printCurrentState(String string) {
        System.out.println(string);
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }
    }
}