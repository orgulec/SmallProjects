package knight_game;

import lombok.Getter;

@Getter
public class Armour extends Equipment {
    private String name;
    private int power;
    private int minusSpeed;
    private int price;
    private final String type;

    public Armour(String name, int power, int minusSpeed, int price) {
        super(name, power, minusSpeed, price);
        this.name=name;
        this.power=power;
        this.minusSpeed=minusSpeed;
        this.price = price;
        this.type="armour";
    }

    @Override
    public String toString() {
        return name +
                " [Power: " + power +
                ", Minus speed: " + minusSpeed +
                "] - price: " + price + " gold coins";
    }
}
