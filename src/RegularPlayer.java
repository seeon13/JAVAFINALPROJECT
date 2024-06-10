public class RegularPlayer extends AbstractPlayer {
    public RegularPlayer(int code, String name, String nationality, int price, int sort) {
        super(code, name, nationality, price, sort);
    }
    @Override
    public String toString() {
        String[] positions = {"ST", "MF", "DF", "GK"};
        int sortIndex = Math.max(0, Math.min(getSort() - 1, positions.length - 1)); // Ensure sort index is within bounds
        String position = positions[sortIndex];
        return "[" + getCode() + "] " + getName() + " | " + getNationality() + " | [" + position + "] | Market Price: " + getPrice();
    }


}
