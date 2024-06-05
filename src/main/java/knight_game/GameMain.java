package own_exercises.knight_game;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameMain {
    static boolean GAME;
    static Knight PLAYER;

    public static void main(String[] args) throws InterruptedException {
        String ls = "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*";
        Knight enemy;
        List<Knight> knightsList = Generator.createKnightsList();
        List<Equipment> equipmentList = Generator.createEquipmentList();

        GAME = true;

        while (GAME) {
            String startText = setStartingText();

            System.out.println(startText);
            if (PLAYER == null) {
                System.out.println("Choose your warrior:");
                knightsList.forEach(System.out::println);

                int first = new Scanner(System.in).nextInt();

                if (first < knightsList.size()) {
                    PLAYER = knightsList.get(first);
                    System.out.println("You choose " + PLAYER.getName() + "!");
                } else {
                    System.out.println("Wrong choose...");
                    PLAYER = new Knight();
                    GAME = false;
                }
            }
            enemy = knightsList.stream()
                    .filter(a -> !a.getName().equals(PLAYER.getName()))
                    .toList().get(new Random().nextInt(knightsList.size() - 1));
            System.out.println("Your enemy is " + enemy.getName() + "!");
            if (enemy.getHp() < 100) enemy.setHp(100);

            List<Equipment> weapons = equipmentList.stream().filter(w -> w.getType().equals("weapon")).toList();
            enemy.setWeapon(weapons.get(new Random().nextInt(weapons.size())));
            System.out.println(enemy.getName() + " has a " + enemy.getWeapon().getName() + "!");
            buyWeaponForPlayer(weapons);

            List<Equipment> armour = equipmentList.stream().filter(a -> a.getType().equals("armour")).toList();
            enemy.setArmour(armour.get(new Random().nextInt(armour.size())));
            System.out.println(enemy.getName() + " has a " + enemy.getArmour().getName() + "!");
            buyArmourForPlayer(armour);

            System.out.println("\nNow it's time to start the fight!");
            System.out.println(ls + "\n");
            System.out.println(PLAYER.describePlayer());
            System.out.println("      VS");
            System.out.println(enemy.describePlayer() + "\n");

            GameUtil playerThread = new GameUtil(PLAYER, enemy);
            GameUtil enemyThread = new GameUtil(enemy, PLAYER);

            ExecutorService battleExecutor = Executors.newFixedThreadPool(3);
            Runnable playerTask = playerThread::run;
            Runnable enemyTask = enemyThread::run;

            while (GAME) {
                battleExecutor.submit(playerTask);
                battleExecutor.submit(enemyTask);
                if (!GAME || PLAYER.getHp() < 1 || enemy.getHp() < 1 || battleExecutor.isShutdown()) {
                    GAME = false;
                    battleExecutor.shutdown();
                }
            }
            battleExecutor.awaitTermination(2000, TimeUnit.MILLISECONDS);

            if (!GAME) {
                if (!battleExecutor.isTerminated()) battleExecutor.shutdownNow();
                setGainsAfterFight();
                System.out.println("\n" + ls);
                continueGameQuestion();
            }
        }
        System.out.println(ls + "\n");
    }

    private static String setStartingText() {
        String startText = "\n=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*\n";
        startText += (PLAYER == null) ? " Welcome in Warrior Fight game!" :
                " " + PLAYER.getName() + " have " + PLAYER.getWins() + " wins and " + PLAYER.getLoses() + " loses.";
        startText += "\n=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*\n";
        return startText;
    }

    private static void continueGameQuestion() {
        System.out.println("If you want to start new fight, press Y. To exit press X.");
        if (new Scanner(System.in).nextLine().equalsIgnoreCase("y")) {
            GAME = true;
        } else {
            System.out.println("Good bye!");
        }
    }

    private static void buyArmourForPlayer(List<Equipment> armour) {
        System.out.println("\nYou still have " + PLAYER.getGold() + " gold coins. You can buy some armour:");
        if (PLAYER.getArmour() != null) System.out.println("(Now you have " + PLAYER.getArmour().getName() + ")");
        armour.forEach(a -> System.out.println("  " + armour.indexOf(a) + " - " + a.toString()));
        System.out.println("Press X to skip...");
        String input2 = new Scanner(System.in).nextLine();

        if (!input2.equalsIgnoreCase("x")) {
            int buy2 = Integer.parseInt(input2);
            if (buy2 < armour.size()) {
                Equipment eq2 = armour.get(buy2);
                if (PLAYER.getGold() >= eq2.getPrice()) {
                    PLAYER.setArmour(eq2);
                    PLAYER.setGold(PLAYER.getGold() - eq2.getPrice());
                    System.out.println("Now you have " + eq2.getName() + "!");
                } else {
                    System.out.println("You have not enough gold coins!");
                }
            }
        }
    }

    private static void buyWeaponForPlayer(List<Equipment> weapons) {
        System.out.println("\nYou have " + PLAYER.getGold() + " gold coins. Buy your weapon:");
        if (PLAYER.getWeapon() != null) System.out.println("(Now you have " + PLAYER.getWeapon().getName() + ")");
        weapons.forEach(w -> System.out.println("  " + weapons.indexOf(w) + " - " + w.toString()));
        System.out.println("Press X to skip...");
        String input = new Scanner(System.in).nextLine();

        if (!input.equalsIgnoreCase("x")) {
            int buy = Integer.parseInt(input);
            if (buy < weapons.size()) {
                Equipment eq1 = weapons.get(buy);
                if (PLAYER.getGold() >= eq1.getPrice()) {
                    PLAYER.setWeapon(eq1);
                    PLAYER.setGold(PLAYER.getGold() - eq1.getPrice());
                    System.out.println("Now you have " + eq1.getName() + "!");
                } else {
                    System.out.println("You don't have enough gold coins!");
                }
            }
        }
    }

    public synchronized static String executeAttackOnEnemy(Knight player, Knight enemy) {
        String result = "";
        if (player.getHp() > 0 && enemy.getHp() > 0) {
            if (player.tryToHit(enemy)) {
                long plAttack = player.makeAttack() / enemy.defence();
                result += showPlayerState(player) + " is attacking " + showPlayerState(enemy) + " and cosing him " + plAttack + " damage!";
                int enemyHpLeft = (int) (enemy.getHp() - plAttack);
                enemy.setHp(enemyHpLeft);

                if (enemy.getHp() <= 0) {
                    result += "\n" + enemy.getName() + " is dead! " + player.getName() + " wins!";
                    enemy.setLoses(enemy.getLoses() + 1);
                    player.setWins(player.getWins() + 1);
                    player.setWinner(true);
                    GAME = false;
                }
            } else {
                result += showPlayerState(player) + " is trying to attack " + showPlayerState(enemy) + " but he cannot hit the enemy!";
            }
        } else {
            GAME = false;
            result += "The battle is over...";
        }
        return result;
    }

    private static void setGainsAfterFight() {
        int factor = PLAYER.isWinner() ? 2 : 1;
        int goldGain = new Random().nextInt(100) + 50 * factor;
        int exp = new Random().nextInt(50) + 75 * factor;
        System.out.println("\nYou gain " + goldGain + " gold coins and " + exp + " Exp!");
        PLAYER.setGold(PLAYER.getGold() + goldGain);
        PLAYER.setHp(100);
        PLAYER.gainLevel(exp);
        PLAYER.setWinner(false);
    }

    private static String showPlayerState(Knight player) {
        return player.getName() + " [" + player.getHp() + "/" + player.getMaxHp() + "]";
    }

}
