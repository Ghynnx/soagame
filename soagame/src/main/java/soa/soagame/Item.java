/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soa.soagame;

/**
 *
 * @author Student's Account
 */
public class Item {
    public enum Type { HP_POTION, MANA_POTION, SWORD, STAFF, SHIELD, HELMET, ARMOR }
    public enum Rarity { COMMON, UNCOMMON, RARE }

    public final String name;
    public final Type type;
    public final int value;
    public final int shopPrice;
    public final Rarity rarity;
    public final String description;

    public Item(String name, Type type, int value, int shopPrice, Rarity rarity, String description) {
        this.name = name; this.type = type; this.value = value; this.shopPrice = shopPrice; this.rarity = rarity; this.description = description;
    }
    public String toString() {
        return name + " [" + rarity + "]";
    }
    public Item copy() {
        return new Item(name, type, value, shopPrice, rarity, description);
    }
}
