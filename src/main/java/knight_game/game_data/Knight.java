package knight_game.game_data;

import lombok.Data;

import java.util.Random;

@Data
public class Knight {
    private String name;
    private int strength;
    private int agility;
    private int endurance;
    private int speed;

    private int maxHp = 100;
    private volatile int hp = 100;
    private int gold = 800;

    private Equipment weapon;
    private Equipment armour;
    private int wins = 0;
    private int loses = 0;
    private boolean winner;
    private int exp = 0;

    public Knight() {
    }

    public Knight(String name, int strength, int agility, int endurance, int speed) {
        this.name = name;
        this.strength = strength;
        this.agility = agility;
        this.endurance = endurance;
        this.speed = speed;
    }

    public void gainLevel(int expGain) {
        int LEVEL = 500;
        exp += expGain;
        if (exp >= LEVEL) {
            exp -= LEVEL;
            strength++;
            endurance++;
            speed++;
            agility++;
            System.out.println("Congratulations!! You have gain next level!!");
        }
    }

    public long makeAttack() {
        long w = weapon != null ? weapon.getPower() : 1L;
        double dmg = 12 * (strength + w) * rand();
        return Math.round(dmg);
    }


    public long defence() {
        long a = armour != null ? armour.getPower() : 1L;
        double def = (endurance + a) / rand();
        return Math.round(def);
    }

    public boolean tryToHit(Knight enemy) {
        double tryToHit = (double) (agility * 75) / enemy.getAgility();
        return new Random().nextInt(99) < tryToHit;
    }

    public long attackDelay() {
        long interval = 800L;
        long playerSpeed = speed * 10L;
        long w = weapon != null ? weapon.getMinusSpeed() : 0L;
        long a = armour != null ? armour.getMinusSpeed() : 0L;
        long minusSpeed = (w + a) * 100L;
        return interval + minusSpeed - playerSpeed;
    }

    private double rand() {
        Random rand = new Random();
        double x = rand.nextInt(15);
        return (100 + x) / 100;
    }

    public String describePlayer() {
        String w = weapon != null ? weapon.getName() : "no weapon";
        String a = armour != null ? armour.getName() : "no armour";
        return name + " with " + w + " and in " + a;
    }

    @Override
    public String toString() {
        return name +
                " [Strength: " + strength +
                ", Endurance: " + endurance +
                ", Agility: " + agility +
                ", Speed: " + speed +
                "] Hp: " + hp + "/" + maxHp;
    }
}
