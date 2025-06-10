package soa.soagame;

public class Skill {
    public interface SkillAction {
        void apply(Player p, Enemy e);
    }

    public String name;
    public int cooldown;
    public String description;
    public int manaCost;
    public SkillAction action;

    public Skill(String name, int cooldown, String description, int manaCost, SkillAction action) {
        this.name = name;
        this.cooldown = cooldown;
        this.description = description;
        this.manaCost = manaCost;
        this.action = action;
    }

    public void use(Player p, Enemy e) {
        if (p.mana < manaCost) {
            System.out.println("  [Not enough mana!]");
            return;
        }
        p.mana -= manaCost;
        action.apply(p, e);
    }
}