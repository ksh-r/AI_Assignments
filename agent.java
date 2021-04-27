/*Team Daemon Demons*/

/* Agent for Puzzle 8:
A breadth-first-search (BFS) algorithm to solve the Puzzle 8 problem.
Note: BFS is used only to analyse the time and space complexity, optimally a heuristic based algorithm
      should be used to solve the problem efficiently
Variables:
    game: object which stores the current game state, computes the moves and checks if goal is reached
    startTime, stopTime: used to calcute total time taken by the BFS function
Functions:
    main: creates new game object, calls BFS function and prints time and space used
    breadthFirstSearch: prints the winning moves given the game object
    copyState: returns a deep copy of current game state
*/

import java.util.LinkedList;
import java.util.Queue;

public class agent {

    static tileGame game;
    private static long startTime, stopTime;
    private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    public static void main(String args[]) {

        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // Run the garbage collector
    
        int depth = 10;
        game = new tileGame();
        game.newDepthGame(depth);
        System.out.println("Game instance depth : " + depth);
        game.printCurrentState("Initial State :");
        // char[][] startState = copyState();
        breadthFirstSearch();
        game.printCurrentState("Final State :");

        // Calculate the used time and memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory: " + memory + " bytes | " + bytesToMegabytes(memory) + " megabytes.");
        System.out.println("Used time: " + (stopTime - startTime) + " milliseconds.");
    }

    public static void breadthFirstSearch() {
        // Marking start time of BFS function
        startTime = System.currentTimeMillis();

        Queue<String> queue = new LinkedList<>();

        String initialMoves = game.getActions(), currMove, goalPath = "", nextMoves;
        char ch;
        for (int i = 0; i < initialMoves.length(); i++)
            queue.add(Character.toString(initialMoves.charAt(i)));

        while (!queue.isEmpty()) {
            currMove = queue.poll();
            // System.out.print(currMove + " "); // To print the queue
            for (int i = 0; i < currMove.length(); i++)
                game.move(currMove.charAt(i));
            if (game.checkGoal()) {
                goalPath = currMove;
                break;
            }
            nextMoves = game.getActions();
            for (int i = 0; i < nextMoves.length(); i++)
                queue.add(currMove + Character.toString(nextMoves.charAt(i)));
            for (int i = currMove.length() - 1; i >= 0; i--) {
                ch = currMove.charAt(i);
                if (ch == 'U')
                    game.move('D');
                else if (ch == 'D')
                    game.move('U');
                else if (ch == 'L')
                    game.move('R');
                else if (ch == 'R')
                    game.move('L');
            }
        }

        // Marking end time of BFS function
        stopTime = System.currentTimeMillis();
        System.out.println("Moves: " + goalPath);
    }

    static char[][] copyState() {
        char[][] arr = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j] = game.state[i][j];
            }
        }
        return arr;
    }
}