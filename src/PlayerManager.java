import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class PlayerManager {
    private List<RegularPlayer> players;
    private List<MyTeamPlayer> myTeam;

    public PlayerManager() {
        players = new ArrayList<>();
        myTeam = new ArrayList<>();
    }

    public int loadData() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("playerlist.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                int code = Integer.parseInt(data[0]);
                String name = data[1];
                String nationality = data[2];
                int price = Integer.parseInt(data[3]);
                int sort = Integer.parseInt(data[4]);
                players.add(new RegularPlayer(code, name, nationality, price, sort));
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return count;
    }

    public void saveData() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("playerlist.txt"))) {
            for (RegularPlayer player : players) {
                pw.println(player.getCode() + " " + player.getName() + " " + player.getNationality() + " " + player.getPrice() + " " + player.getSort());
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter("my_playerlist.txt"))) {
            for (MyTeamPlayer player : myTeam) {
                pw.println(player.getCode() + " " + player.getName() + " " + player.getNationality() + " " + player.getPrice() + " " + player.getSort());
            }
        } catch (IOException e) {
            System.out.println("Error saving my team: " + e.getMessage());
        }
    }

    public List<RegularPlayer> getPlayers() {
        return players;
    }

    public void printAllPlayers() {
        for (RegularPlayer player : players) {
            System.out.println(player);
        }
    }

    public void printMyTeam() {
        for (MyTeamPlayer player : myTeam) {
            System.out.println(player);
        }
    }

    public void addNewPlayer(Scanner scanner) {
        System.out.print("Enter player back number: ");
        int code = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Check for duplicate code
        boolean isDuplicated = false;
        for (RegularPlayer p : players) {
            if (p.getCode() == code) {
                isDuplicated = true;
                break;
            }
        }

        if (isDuplicated) {
            System.out.println("Player with this back number already exists!");
            return;
        }

        System.out.print("Enter player name: ");
        String name = scanner.nextLine();
        System.out.print("Enter nationality: ");
        String nationality = scanner.nextLine();
        System.out.print("Enter market price: ");
        int price = scanner.nextInt();
        System.out.print("Enter position (1: ST, 2: MF, 3: DF, 4: GK): ");
        int sort = scanner.nextInt();

        players.add(new RegularPlayer(code, name, nationality, price, sort));
        System.out.println("Player added successfully.");
    }

    public void modifyPlayer(Scanner scanner) {
        System.out.print("Enter player back number to modify: ");
        int code = scanner.nextInt();
        scanner.nextLine(); // consume newline

        RegularPlayer player = null;
        for (RegularPlayer p : players) {
            if (p.getCode() == code) {
                player = p;
                break;
            }
        }

        if (player == null) {
            System.out.println("No player found with this back number.");
            return;
        }

        System.out.println("Current: " + player);
        System.out.print("Enter new player name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new nationality: ");
        String nationality = scanner.nextLine();
        System.out.print("Enter new market price: ");
        int price = scanner.nextInt();
        System.out.print("Enter new position (1: ST, 2: MF, 3: DF, 4: GK): ");
        int sort = scanner.nextInt();

        // Update player fields directly
        player.name = name;
        player.nationality = nationality;
        player.price = price;
        player.sort = sort;

        System.out.println("Player modified successfully.");
    }


    public void searchPlayer(String name) {
        int count = 0; // Number of players found

        // Convert input name to lowercase (or uppercase) for case-insensitive comparison
        String searchNameLower = name.toLowerCase();

        // Iterate through all players to find matches
        for (RegularPlayer player : players) {
            // Convert player name to lowercase (or uppercase) for case-insensitive comparison
            String playerNameLower = player.getName().toLowerCase();

            // Check if the player's name contains the input name (case-insensitive)
            if (playerNameLower.contains(searchNameLower)) {
                System.out.println(player); // Print player information
                count++; // Increment count of players found
            }
        }

        // Print search result
        if (count == 0) {
            System.out.println("No player found with the given name.");
        } else {
            System.out.println(count + " player(s) found.");
        }
    }


    public void buyPlayer(Scanner scanner) {
        System.out.print("Enter player back number or name to buy: ");
        String input = scanner.nextLine().trim(); // Read input (back number or name) and trim leading/trailing whitespace

        // Check if input can be parsed as an integer (back number)
        int backNumber = 0;
        boolean isBackNumber = false;
        try {
            backNumber = Integer.parseInt(input);
            isBackNumber = true;
        } catch (NumberFormatException e) {
            // Input is not a valid back number
        }

        // Flag to track if player is found
        boolean playerFound = false;

        // Search for player by back number or name
        for (RegularPlayer p : players) {
            if ((isBackNumber && p.getCode() == backNumber) || (!isBackNumber && p.getName().equalsIgnoreCase(input))) {
                playerFound = true;

                // Check for duplicate code in myTeam
                boolean isDuplicated = false;
                for (MyTeamPlayer myTeamPlayer : myTeam) {
                    if (myTeamPlayer.getCode() == p.getCode()) {
                        isDuplicated = true;
                        break;
                    }
                }

                if (isDuplicated) {
                    System.out.println("You already bought this player!");
                    return;
                }

                myTeam.add(new MyTeamPlayer(p.getCode(), p.getName(), p.getNationality(), p.getPrice(), p.getSort()));
                System.out.println("Player bought successfully.");
                break; // Exit loop after first match
            }
        }

        // If player is not found
        if (!playerFound) {
            System.out.println("No player found with the given back number or name.");
        }
    }

    public void deletePlayer(Scanner scanner) {
        System.out.print("> Enter the back number or name of the player you want to delete: ");
        String input = scanner.nextLine().trim(); // Read input (back number or name) and trim leading/trailing whitespace

        // Check if input can be parsed as an integer (back number)
        int backNumber = 0;
        boolean isBackNumber = false;
        try {
            backNumber = Integer.parseInt(input);
            isBackNumber = true;
        } catch (NumberFormatException e) {
            // Input is not a valid back number
        }

        // Flag to track if player is found
        boolean playerFound = false;

        // Search for player by back number or name
        for (Iterator<RegularPlayer> iterator = players.iterator(); iterator.hasNext();) {
            RegularPlayer player = iterator.next();
            if ((isBackNumber && player.getCode() == backNumber) || (!isBackNumber && player.getName().equalsIgnoreCase(input))) {
                playerFound = true;
                System.out.println("> Player Info.");
                System.out.println(player);
                System.out.print("> Do you want to delete the player? (1: Yes, 0: No) > ");
                int yesNo = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (yesNo == 1) {
                    iterator.remove(); // Remove player from list
                    System.out.println("> Player is deleted.");
                }
                break; // Exit loop after first match
            }
        }

        // If player is not found
        if (!playerFound) {
            System.out.println("No player found with the given back number or name.");
        }
    }

}
