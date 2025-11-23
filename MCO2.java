import java.io.*;
import java.util.*;

/**
 * A social network graph analyzer that manages friendship connections between
 * accounts.
 */
public class MCO2 {

    private ArrayList<ArrayList<Integer>> graph;
    private int numAccounts;
    private Scanner sc;
    private String loadedFilePath;

    /**
     * Initializes MCO2 fields to default values
     */
    public MCO2() {
        graph = new ArrayList<>();
        sc = new Scanner(System.in);
        numAccounts = 0;
        loadedFilePath = "";
    }

    /**
     * Clears the console screen
     */
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Loads the graph from a text file containing account and friendship data.
     * The file format should have the number of accounts and edges on the first
     * line, followed by lines representing friendships between accounts.
     * 
     * @param filePath the path to the input file
     * @return true if the graph was loaded successfully, false otherwise
     */
    public boolean loadGraph(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String firstLine = reader.readLine();
            if (firstLine == null) {
                System.out.println("Error: File is empty");
                reader.close();
                return false;
            }

            String[] firstLineParts = firstLine.trim().split("\\s+");
            numAccounts = Integer.parseInt(firstLineParts[0]);

            for (int i = 0; i < numAccounts; i++) {
                graph.add(new ArrayList<Integer>());
            }

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] friendshipPair = currentLine.trim().split("\\s+");
                int personA = Integer.parseInt(friendshipPair[0]);
                int personB = Integer.parseInt(friendshipPair[1]);

                if (!graph.get(personA).contains(personB)) {
                    graph.get(personA).add(personB);
                }
                if (!graph.get(personB).contains(personA)) {
                    graph.get(personB).add(personA);
                }
            }

            reader.close();
            loadedFilePath = filePath;
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
     * Validates whether a given ID is within the valid range of account IDs.
     * 
     * @param id the account ID to validate
     * @return true if the ID is valid (between 0 and numAccounts-1), false
     *         otherwise
     */
    public boolean isValidId(int id) {
        return id >= 0 && id < numAccounts;
    }

    /**
     * Displays the friend list for a given person ID.
     * Shows the number of friends and lists all friend IDs.
     * If the ID is invalid, displays an error message.
     * 
     * @param personId the ID of the person whose friend list to display
     */
    public void displayFriendList(int personId) {
        clearScreen();

        System.out.println();
        System.out.println("═══════════════════════════════════════");
        System.out.println("         GET FRIEND LIST");
        System.out.println("═══════════════════════════════════════");
        System.out.println();

        if (!isValidId(personId)) {
            System.out.println("Error: Invalid ID! ID must be between 0 and " + (numAccounts - 1));
            System.out.println();
            System.out.println("═══════════════════════════════════════");
            System.out.println("Press Enter to continue...");
            sc.nextLine();
            return;
        }

        ArrayList<Integer> friends = graph.get(personId);
        int numFriends = friends.size();

        System.out.println("Person " + personId + " has " + numFriends + " friends!");
        System.out.println();

        if (numFriends > 0) {
            System.out.print("List of friends: ");
            for (int i = 0; i < numFriends; i++) {
                System.out.print(friends.get(i));
                if (i < numFriends - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        } else {
            System.out.println("This person has no friends in the network.");
        }
        System.out.println();
        System.out.println("═══════════════════════════════════════");
        System.out.println("Press Enter to continue...");
        sc.nextLine();
    }

    /**
     * Finds the shortest path connection between two people using BFS.
     * Returns the complete path from the first person to the second person if a
     * connection exists.
     * 
     * @param personId1 the ID of the first person
     * @param personId2 the ID of the second person
     * @return a List of Integer IDs representing the path from personId1 to
     *         personId2,
     *         or null if no connection exists or if either ID is invalid
     */
    public List<Integer> displayConnection(int personId1, int personId2) {
        if (!isValidId(personId1) || !isValidId(personId2))
            return null;

        Queue<List<Integer>> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.add(Arrays.asList(personId1));
        visited.add(personId1);

        while (queue.size() != 0) {
            List<Integer> path = queue.poll();
            int last = path.getLast();

            if (last == personId2) {
                return path;
            }

            for (int neighbor : graph.get(last)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    List<Integer> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }
        return null;
    }

    /**
     * Displays and handles the main menu interface for the program.
     * Provides options to get friend lists, find connections between people, or
     * exit.
     * Continues running until the user chooses to exit.
     */
    public void runMainMenu() {
        boolean running = true;
        while (running) {
            clearScreen();

            System.out.println();
            System.out.println("═══════════════════════════════════════");
            System.out.println("           MAIN MENU");
            System.out.println("═══════════════════════════════════════");
            System.out.println();
            System.out.println("  File: " + loadedFilePath);
            System.out.println("  Accounts: " + numAccounts);
            System.out.println();
            System.out.println("  [1] Get friend list");
            System.out.println("  [2] Get connection");
            System.out.println("  [3] Exit");
            System.out.println();
            System.out.println("═══════════════════════════════════════");
            System.out.print("Enter your choice: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    clearScreen();

                    System.out.println();
                    System.out.println("═══════════════════════════════════════");
                    System.out.println("         GET FRIEND LIST");
                    System.out.println("═══════════════════════════════════════");
                    System.out.println();

                    try {
                        System.out.print("Enter ID of person: ");
                        int personId = sc.nextInt();
                        sc.nextLine();
                        displayFriendList(personId);
                    } catch (InputMismatchException e) {
                        clearScreen();

                        System.out.println();
                        System.out.println("═══════════════════════════════════════");
                        System.out.println("         GET FRIEND LIST");
                        System.out.println("═══════════════════════════════════════");
                        System.out.println();
                        System.out.println("Error: Please enter a valid integer ID");
                        System.out.println();
                        System.out.println("═══════════════════════════════════════");
                        System.out.println("Press Enter to continue...");
                        sc.nextLine(); 
                        sc.nextLine();
                    }
                    break;

                case "2":
                    clearScreen();

                    System.out.println();
                    System.out.println("═══════════════════════════════════════");
                    System.out.println("          GET CONNECTION");
                    System.out.println("═══════════════════════════════════════");
                    System.out.println();

                    try {
                        System.out.print("Enter ID of first person: ");
                        int person1 = sc.nextInt();
                        sc.nextLine();
                        System.out.println();
                        System.out.print("Enter ID of second person: ");
                        int person2 = sc.nextInt();
                        sc.nextLine();

                        List<Integer> connection = displayConnection(person1, person2);

                        clearScreen();

                        System.out.println();
                        System.out.println("═══════════════════════════════════════");
                        System.out.println("          GET CONNECTION");
                        System.out.println("═══════════════════════════════════════");
                        System.out.println();

                        if (!isValidId(person1) || !isValidId(person2)) {
                            System.out.println("No connection found ");
                        } else if (connection != null && !connection.isEmpty()) {
                            System.out.println("There is a connection from " + person1 + " to " + person2 + "!");
                            System.out.println();
                            for (int i = 0; i < connection.size() - 1; i++) {
                                int from = connection.get(i);
                                int to = connection.get(i + 1);
                                System.out.println("  " + from + " is friends with " + to);
                            }
                        } else {
                            System.out.println("No connection found ");
                        }

                        System.out.println();
                        System.out.println("═══════════════════════════════════════");
                        System.out.println("Press Enter to continue...");
                        sc.nextLine();

                    } catch (InputMismatchException e) {
                        System.out.println();
                        System.out.println("Error: Please enter a valid integer ID");
                        System.out.println();
                        System.out.println("═══════════════════════════════════════");
                        System.out.println("Press Enter to continue...");
                        sc.nextLine(); 
                        sc.nextLine(); 
                    }
                    break;
                case "3":
                    clearScreen();
                    running = false;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Main entry point of the program.
     * Prompts the user for a file path, loads the graph, and launches the main menu
     * if the graph is loaded successfully.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Input File Path: ");
        String filePath = sc.nextLine().trim();

        MCO2 program = new MCO2();

        if (program.loadGraph(filePath)) {
            System.out.println("Graph loaded!");
            System.out.println("Press Enter to continue...");
            sc.nextLine();
            program.runMainMenu();
        } else {
            System.out.println("Failed to load graph.");
        }
        if (sc != null) {
            sc.close();
        }
    }
}