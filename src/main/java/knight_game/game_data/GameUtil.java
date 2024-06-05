package knight_game;

public class GameUtil implements Runnable{
    private Knight player;
    private Knight enemy;
    long delay;

    public GameUtil(Knight player, Knight enemy) {
        this.player = player;
        this.enemy = enemy;
        delay = player.attackDelay();
    }

    @Override
    public void run() {
        try{
            Thread.sleep(delay);
            Thread.currentThread().interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(player.getHp()>0 && enemy.getHp()>0) {
            System.out.println(GameMain.executeAttackOnEnemy(player, enemy));
        } else Thread.currentThread().interrupt();
    }
}