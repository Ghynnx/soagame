/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soa.soagame;

/**
 *
 * @author Student's Account
 */
public class Enemy {
    public final String name;
    public final int maxHp;
    public int hp, minAtk, maxAtk, def, level, mana, expReward, goldReward;
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
        int dmg = minAtk + (int)(Math.random() * (maxAtk - minAtk + 1));
        if ("tank".equals(p.playerClass)) dmg = Math.max(0, dmg - 5);
        dmg = Math.max(0, dmg - p.level);
        p.hp -= dmg;
        System.out.println(name + " attacks you for " + dmg + " damage!");
    }
}