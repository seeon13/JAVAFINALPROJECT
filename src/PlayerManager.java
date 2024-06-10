import java.io.*;
import java.util.*;

public class PlayerManager {
    private List<Player> players;
    private List<Player> myTeam;

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
                players.add(new Player(code, name, nationality, price, sort));
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return count;
    }

    public void saveData() {
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

    public void printAllPlayers() {
        for (Player player : players) {
            System.out.println(player);
        }
    }

    public void printMyTeam() {
        for (Player player : myTeam) {
            System.out.println(player);
        }
    }

    public void addNewPlayer(Scanner scanner) {
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

    public void modifyPlayer(Scanner scanner) {
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

    public void searchPlayer(String name) {
        players.stream().filter(p -> p.name.contains(name)).forEach(System.out::println);
    }

    public void buyPlayer(Scanner scanner) {
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

    public void deletePlayer(Scanner scanner) {
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
