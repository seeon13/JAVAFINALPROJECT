import java.util.Scanner;

public class SoccerPlayerTransferMarket {
    public static void main(String[] args) {
        PlayerManager manager = new PlayerManager();
        Scanner scanner = new Scanner(System.in);
        int count = manager.loadData();
        System.out.println("Soccer Player Transfer Market");
        System.out.println("> Load " + count + " players.");
        while (true) {
            System.out.println("\n> Menu 1.List 2.Add 3.Modify 4.Search 5.Buy 6.My Squad 7.Save 8.Delete 0.Quit");
            System.out.print(">> Menu? > ");
            int no = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (no) {
                case 1:
                    System.out.println("> 1.Player list (" + manager.getPlayers().size() + " players)");
                    manager.printAllPlayers();
                    break;
                case 2:
                    System.out.println("> 2.Add a player");
                    manager.addNewPlayer(scanner);
                    break;
                case 3:
                    System.out.println("> 3.Modify a player");
                    manager.modifyPlayer(scanner);
                    break;
                case 4:
                    System.out.println("> 4.Search a Player");
                    System.out.print(">> Enter player's name > ");
                    String name = scanner.nextLine();
                    manager.searchPlayer(name);
                    break;
                case 5:
                    System.out.println("> 5.Buy a player");
                    manager.buyPlayer(scanner);
                    break;
                case 6:
                    System.out.println("> 6.My Team");
                    manager.printMyTeam();
                    break;
                case 7:
                    System.out.println("> 7.Save");
                    manager.saveData();
                    System.out.println("\n> All data are saved.");
                    break;
                case 8:
                    System.out.println("> 8.Delete");
                    manager.deletePlayer(scanner);
                    break;
                case 0:
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid menu option.");
            }
        }
    }
}
