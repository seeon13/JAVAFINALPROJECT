public class AbstractPlayer implements MarketEntity {
    protected int code;
    protected String name;
    protected String nationality;
    protected int price;
    protected int sort;

    public AbstractPlayer(int code, String name, String nationality, int price, int sort) {
        this.code = code;
        this.name = name;
        this.nationality = nationality;
        this.price = price;
        this.sort = sort;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNationality() {
        return nationality;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getSort() {
        return sort;
    }

    @Override
    public void displayInfo() {
        String[] positions = {"ST", "MF", "DF", "GK"};
        System.out.println("[" + code + "] " + name + " | " + nationality + " | [" + positions[sort - 1] + "] | Market Price: " + price);
    }
}
