/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soa.soagame;

/**
 *
 * @author Student's Account
 */
public class Skill {
    public final String name;
    public final int cooldown;
    public final String description;
    private final SkillAction action;

    public Skill(String name, int cooldown, String description, SkillAction action) {
        this.name = name; this.cooldown = cooldown; this.description = description; this.action = action;
    }

    public void use(Player p, Enemy e) { action.execute(p, e); }

    interface SkillAction {
        void execute(Player p, Enemy e);
    }
}
