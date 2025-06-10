/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soa.soagame;

/**
 *
 * @author Student's Account
 */
import java.util.*;

public class Player {
    public static final Set<String> START_CLASSES = Set.of("warrior", "mage", "archer", "tank");

    public final String name, playerClass;
    public int level = 1, hp = 100, mana = 30, exp = 0, gold = 50, expToLevel = 100;
    public String passiveSkill = "None";
    public final Inventory inventory = new Inventory();
    public final List<Skill> skills = new ArrayList<>();
    public final Map<Skill, Integer> cooldowns = new HashMap<>();
    private StoryManager story;

    public Player(String name, String playerClass, StoryManager story) {
        this.name = name;
        this.playerClass = playerClass;
        this.story = story;

        switch (playerClass) {
            case "warrior":
                passiveSkill = "Berserk (gain extra attack if HP below 30%)";
                addSkill(new Skill("Power Strike", 3, "A heavy blow dealing 2x damage.", (p, e) -> {
                    int dmg = (10 + p.level * 2) * 2;
                    System.out.println("You use Power Strike for " + dmg + " damage!");
                    e.hp -= Math.max(0, dmg - e.def);
                }));
                addSkill(new Skill("Stunning Blow", 4, "Stuns the enemy for 1 turn.", (p, e) -> {
                    System.out.println("You use Stunning Blow! The enemy is stunned!");
                    e.stunned = true;
                }));
                addSkill(new Skill("Second Wind", 5, "Restore 30% HP instantly.", (p, e) -> {
                    int heal = (int)(p.maxHp() * 0.3);
                    p.hp = Math.min(p.maxHp(), p.hp + heal);
                    System.out.println("You recover " + heal + " HP with Second Wind!");
                }));
                break;
            case "mage":
                passiveSkill = "Mana Regen (regenerate mana every turn)";
                addSkill(new Skill("Fireball", 2, "Deals 25 magic damage.", (p, e) -> {
                    System.out.println("You cast Fireball for 25 damage!");
                    e.hp -= Math.max(0, 25 - e.def);
                }));
                addSkill(new Skill("Ice Shard", 3, "Deals 15 magic damage and stuns for 1 turn.", (p, e) -> {
                    System.out.println("You cast Ice Shard for 15 damage and stun!");
                    e.hp -= Math.max(0, 15 - e.def); e.stunned = true;
                }));
                addSkill(new Skill("Arcane Recovery", 4, "Restore 40 mana instantly.", (p, e) -> {
                    p.mana = Math.min(p.maxMana(), p.mana + 40);
                    System.out.println("You regain 40 mana!");
                }));
                break;
            case "archer":
                passiveSkill = "Critical (chance to deal double damage)";
                addSkill(new Skill("Double Shot", 2, "Shoots two arrows, each for normal attack.", (p, e) -> {
                    int dmg1 = 10 + p.level * 2;
                    int dmg2 = 10 + p.level * 2;
                    System.out.println("You use Double Shot for " + dmg1 + " and " + dmg2 + " damage!");
                    e.hp -= Math.max(0, dmg1 - e.def);
                    e.hp -= Math.max(0, dmg2 - e.def);
                }));
                addSkill(new Skill("Poison Arrow", 3, "Poison enemy for 3 turns.", (p, e) -> {
                    System.out.println("You shoot Poison Arrow! Enemy is poisoned!");
                    e.poisonedTurns = 3;
                }));
                addSkill(new Skill("Evasion", 4, "Avoids next attack.", (p, e) -> {
                    System.out.println("You prepare to evade the next attack!");
                }));
                break;
            case "tank":
                passiveSkill = "Block (reduce damage by 5)";
                addSkill(new Skill("Shield Bash", 2, "Deals 10 damage and stuns.", (p, e) -> {
                    System.out.println("You use Shield Bash for 10 damage and stun!");
                    e.hp -= Math.max(0, 10 - e.def); e.stunned = true;
                }));
                addSkill(new Skill("Fortify", 4, "Increase defense by 5 for 3 turns.", (p, e) -> {
                    System.out.println("You use Fortify! Defense up!");
                }));
                addSkill(new Skill("Taunt", 3, "Reduces enemy attack for 2 turns.", (p, e) -> {
                    System.out.println("You taunt the enemy! Their attack is reduced!");
                }));
                break;
        }
    }

    public int maxHp() { return 100 + level * 20; }
    public int maxMana() { return 30 + level * 10; }

    public void attack(Enemy e) {
        int dmg = 10 + level * 2;
        if ("archer".equals(playerClass) && Math.random() < 0.25) {
            dmg *= 2; System.out.println("Critical hit!");
        }
        if ("warrior".equals(playerClass) && hp < maxHp() * 0.3) {
            dmg += 5; System.out.println("Berserk power!");
        }
        e.hp -= Math.max(0, dmg - e.def);
        System.out.println("You attack for " + dmg + " damage!");
    }

    public void gainExp(int amt) {
        exp += amt;
        while (exp >= expToLevel) {
            exp -= expToLevel;
            level++;
            expToLevel += 50;
            System.out.println("Level up! You are now level " + level + "!");
        }
    }

    public void useItem(int idx) {
        if (idx < 0 || idx >= inventory.size()) {
            System.out.println("No such item.");
            return;
        }
        Item item = inventory.getItem(idx);
        switch (item.type) {
            case HP_POTION:
                hp = Math.min(maxHp(), hp + item.value);
                System.out.println("Recovered HP!");
                break;
            case MANA_POTION:
                mana = Math.min(maxMana(), mana + item.value);
                System.out.println("Recovered Mana!");
                break;
            default:
                System.out.println("Equipped " + item.name + "!");
                break;
        }
        inventory.removeItem(idx);
    }

    public void applyPassives() {
        if ("mage".equals(playerClass) && mana < maxMana())
            mana = Math.min(maxMana(), mana + 2);
    }

    public void restore() {
        hp = maxHp();
        mana = maxMana();
    }

    private void addSkill(Skill skill) {
        skills.add(skill);
        cooldowns.put(skill, 0);
    }

    public void displaySkillMenuAndUse(Enemy e, Scanner sc) {
        System.out.println("Your Skills (Cooldown in turns):");
        for (int i = 0; i < skills.size(); i++) {
            Skill sk = skills.get(i);
            int cd = cooldowns.getOrDefault(sk, 0);
            System.out.printf("%d. %s - %s [Cooldown: %d, %s]\n", i+1, sk.name, sk.description, sk.cooldown,
                    (cd == 0 ? "Ready" : "CD: "+cd));
        }
        System.out.println("Choose skill number to use (0 to cancel):");
        String skillChoice = sc.nextLine().trim();
        try {
            int idx = Integer.parseInt(skillChoice) - 1;
            if (idx >= 0 && idx < skills.size()) {
                Skill sk = skills.get(idx);
                int cd = cooldowns.get(sk);
                if (cd > 0) {
                    System.out.println("Skill is on cooldown for " + cd + " more turn(s).");
                } else {
                    sk.use(this, e);
                    cooldowns.put(sk, sk.cooldown);
                }
            }
        } catch (NumberFormatException ignored) {}
    }

    public void decrementCooldowns() {
        for (Skill sk : skills) {
            int cd = cooldowns.getOrDefault(sk, 0);
            if (cd > 0) cooldowns.put(sk, cd - 1);
        }
    }
}