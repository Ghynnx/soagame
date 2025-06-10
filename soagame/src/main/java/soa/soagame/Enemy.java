package soa.soagame;

public class Enemy {
    public String name;
    public int hp, maxHp, minAtk, maxAtk, def, level, mana, expReward, goldReward;
    public boolean stunned = false;
    public int poisonedTurns = 0;

    public Enemy(String name, int hp, int minAtk, int maxAtk, int def, int level, int mana, int expReward, int goldReward) {
        this.name = name;
        this.hp = this.maxHp = hp;
        this.minAtk = minAtk;
        this.maxAtk = maxAtk;
        this.def = def;
        this.level = level;
        this.mana = mana;
        this.expReward = expReward;
        this.goldReward = goldReward;
    }

    public void attack(Player p) {
        int atk = minAtk + (int)(Math.random() * (maxAtk - minAtk + 1));
        int damage = Math.max(0, atk - p.getDefenseBonus());
        p.hp -= damage;
        System.out.printf("  %s attacks for %d damage!\n", name, damage);
    }
}