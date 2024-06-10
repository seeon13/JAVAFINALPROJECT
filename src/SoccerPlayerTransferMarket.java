import java.io.*;
import java.util.*;

class Player {
    int code; // back number of player
    String name; // player name
    String nationality; // nationality of player
    int price; // price of player
    int sort; // position of player (1: ST 2: MF 3: DF 4: GK)

    public Player(int code, String name, String nationality, int price, int sort) {
        this.code = code;
        this.name = name;
        this.nationality = nationality;
        this.price = price;
        this.sort = sort;
    }

    @Override
    public String toString() {
        String[] positions = {"ST", "MF", "DF", "GK"};
        return "[" + code + "] " + name + " | " + nationality + " | [" + positions[sort - 1] + "] | Market Price: " + price;
    }
}

public class SoccerPlayerTransferMarket {
    private static List<Player> players = new ArrayList<>();
    private static List<Player> myTeam = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = loadData();
        System.out.println("Soccer Player Transfer Market");
        System.out.println("> Load " + count + " players.");

        while (true) {
            System.out.println("\n> Menu 1.List 2.Add 3.Modify 4.Search 5.Buy 6.My Squad 7.Save 8.Delete 0.Quit");
            System.out.print(">> Menu? > ");
            int no = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (no) {
                case 1:
                    System.out.println("> 1.Player list (" + players.size() + " players)");
                    printAllPlayers();
                    break;
                case 2:
                    System.out.println("> 2.Add a player");
                    addNewPlayer(scanner);
                    break;
                case 3:
                    System.out.println("> 3.Modify a player");
                    modifyPlayer(scanner);
                    break;
                case 4:
                    System.out.println("> 4.Search a Player");
                    System.out.print(">> Enter player's name(CAPITAL) > ");
                    String name = scanner.nextLine();
                    searchPlayer(name);
                    break;
                case 5:
                    System.out.println("> 5.Buy a player");
                    buyPlayer(scanner);
                    break;
                case 6:
                    System.out.println("> 6.My Team");
                    printMyTeam();
                    break;
                case 7:
                    System.out.println("> 7.Save");
                    saveData();
                    System.out.println("\n> All data are saved.");
                    break;
                case 8:
                    System.out.println("> 8.Delete");
                    deletePlayer(scanner);
                    break;
                case 0:
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid menu option.");
            }
        }
    }

    private static int loadData() {
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
                players.add(new Player(code, name, nationality, price, sort));
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return count;
    }

    private static void saveData() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("playerlist.txt"))) {
            for (Player player : players) {
                pw.println(player.code + " " + player.name + " " + player.nationality + " " + player.price + " " + player.sort);
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter("my_playerlist.txt"))) {
            for (Player player : myTeam) {
                pw.println(player);
            }
        } catch (IOException e) {
            System.out.println("Error saving my team: " + e.getMessage());
        }
    }

    private static void printAllPlayers() {
        for (Player player : players) {
            System.out.println(player);
        }
    }

    private static void printMyTeam() {
        for (Player player : myTeam) {
            System.out.println(player);
        }
    }

    private static void addNewPlayer(Scanner scanner) {
        while (true) {
            System.out.print(">> Player Back number > ");
            int code = scanner.nextInt();
            scanner.nextLine(); // consume newline
            boolean isDuplicated = players.stream().anyMatch(p -> p.code == code);

            if (isDuplicated) {
                System.out.println(">> It is duplicated! Retry.");
            } else {
                System.out.print("> Enter new player name > ");
                String name = scanner.nextLine();
                System.out.print("> Enter new nationality > ");
                String nationality = scanner.nextLine();
                System.out.print("> Enter new price > ");
                int price = scanner.nextInt();
                System.out.print("> Enter new position(1:ST 2:MF 3:DF 4:GK) > ");
                int sort = scanner.nextInt();
                players.add(new Player(code, name, nationality, price, sort));
                break;
            }
        }
    }

    private static void modifyPlayer(Scanner scanner) {
        System.out.print(">> Enter a back number of player > ");
        int code = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Player player = players.stream().filter(p -> p.code == code).findFirst().orElse(null);

        if (player != null) {
            System.out.println("Current : " + player);
            System.out.print("> Enter new player name > ");
            player.name = scanner.nextLine();
            System.out.print("> Enter new nationality > ");
            player.nationality = scanner.nextLine();
            System.out.print("> Enter new price > ");
            player.price = scanner.nextInt();
            System.out.print("> Enter new position(1:ST 2:MF 3:DF 4:GK) > ");
            player.sort = scanner.nextInt();
            System.out.println("> Modified.");
        } else {
            System.out.println("> No such player.");
        }
    }

    private static void searchPlayer(String name) {
        players.stream().filter(p -> p.name.contains(name)).forEach(System.out::println);
    }

    private static void buyPlayer(Scanner scanner) {
        while (true) {
            System.out.print("Enter a back number > ");
            int code = scanner.nextInt();
            scanner.nextLine(); // consume newline

            boolean isDuplicated = myTeam.stream().anyMatch(p -> p.code == code);
            if (isDuplicated) {
                System.out.println(">> It is duplicated! Retry.");
                continue;
            }

            Player player = players.stream().filter(p -> p.code == code).findFirst().orElse(null);
            if (player == null) {
                System.out.println("No such player.");
                continue;
            }

            myTeam.add(player);
            System.out.println("Player : " + player);
            System.out.print("Add more?(1:Yes, 0:No) > ");
            int yesNo = scanner.nextInt();
            if (yesNo == 0) break;
        }
    }

    private static void deletePlayer(Scanner scanner) {
        System.out.print("> Enter a number of player (Not backnumber, but number of playerlist) > ");
        int index = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (index < 1 || index > players.size()) {
            System.out.println("Wrong number.");
        } else {
            Player player = players.get(index - 1);
            System.out.println("> Player Info.");
            System.out.println(player);
            System.out.print("> Do you want to delete the player?(1:Yes 0:No) > ");
            int yesNo = scanner.nextInt();
            if (yesNo == 1) {
                players.set(index - 1, players.get(players.size() - 1));
                players.remove(players.size() - 1);
                System.out.println("> Player is deleted.");
            }
        }
    }
}
