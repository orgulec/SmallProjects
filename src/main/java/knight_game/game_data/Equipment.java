package knight_game;

import lombok.Getter;

@Getter
public abstract class Equipment {
    private String name;
    private int power;
    private int minusSpeed;
    private int price;
    private String type;
    public Equipment(String name, int power, int minusSpeed, int price) {
        this.name=name;
        this.power=power;
        this.minusSpeed=minusSpeed;
        this.price = price;
    }

    public Equipment() {
    }
}
