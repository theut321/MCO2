import java.io.*;
import java.util.*;

public class MCO2 {

    private ArrayList<ArrayList<Integer>> graph;
    private int numAccounts;
    private Scanner sc;

    public MCO2() {
        graph = new ArrayList<>();
        sc = new Scanner(System.in);
    }

    /**
     * Loads the graph from a text file containing account and friendship data.
     * 
     * @param filePath the path to the input file
     *                 The file format should be:
     *                 First line: n (number of accounts) and e (number of friendships)
     *                 Each following line: two integers representing a friendship (seperated by space)
     * @return true if the graph was loaded successfully, false otherwise
     * @throws FileNotFoundException if the file does not exist
     * @throws IOException           if there is a problem reading the file
     * @throws NumberFormatException if the file format is invalid
     */
    
    public boolean loadGraph(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            // reads first line to get number of accounts
            String firstLine = reader.readLine();
            if (firstLine == null) {
                System.out.println("Error: File is empty");
                reader.close();
                return false;
            }

            String[] firstLineParts = firstLine.trim().split("\\s+");
            numAccounts = Integer.parseInt(firstLineParts[0]);

            // creates empty friend list for each account
            for (int i = 0; i < numAccounts; i++) {
                graph.add(new ArrayList<Integer>());
            }

            // reads each friendship line
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] friendshipPair = currentLine.trim().split("\\s+");
                int personA = Integer.parseInt(friendshipPair[0]);
                int personB = Integer.parseInt(friendshipPair[1]);

                // adds friendship for person A
                if (!graph.get(personA).contains(personB)) {
                    graph.get(personA).add(personB);
                }
                // adds friendship for person B
                if (!graph.get(personB).contains(personA)) {
                    graph.get(personB).add(personA);
                }
            }

            reader.close();
            return true;

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + filePath);
            return false;
        } catch (IOException e) {
            System.out.println("Error: Problem reading file - " + e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid file format");
            return false;
        }
    }

     /**
     * Displays the friend list for a given person ID
     */
    public void displayFriendList(int personId) {
        // case [1]: Get Friend List
    }

    /**
     * Finds and displays the connection between two people using BFS
     * Returns the shortest path if it exists
     */
    public void displayConnection(int personId) {
        // case [2]: Get Connection
    }

    public void runMainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("MAIN MENU");
            System.out.println("[1] Get Friend List");
            System.out.println("[2] Get Connection");
            System.out.println("[3] Exit");
            System.out.println("Enter your choice:");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    // STILL EMPTY: Feature not yet implemented
                    System.out.println("Feature [1] coming soon...");
                    break;
                case "2":
                    // STILL EMPTY: Feature not yet implemented
                    System.out.println("Feature [2] coming soon...");
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input File Path:");
        String filePath = sc.nextLine().trim();

        MCO2 program = new MCO2();

        if (program.loadGraph(filePath)) {
            System.out.println("Graph loaded!");
            program.runMainMenu();
        } else {
            System.out.println("Failed to load graph.");
        }
    }
}
