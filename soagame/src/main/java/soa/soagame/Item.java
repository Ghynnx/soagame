package soa.soagame;

public class Item {
    public enum Type {
        HP_POTION, MANA_POTION, SWORD, STAFF, SHIELD, HELMET, ARMOR
    }

    public enum Rarity {
        COMMON, UNCOMMON, RARE
    }

    public String name;
    public Type type;
    public int value;
    public int shopPrice;
    public Rarity rarity;
    public String description;

    public Item(String name, Type type, int value, int shopPrice, Rarity rarity, String description) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.shopPrice = shopPrice;
        this.rarity = rarity;
        this.description = description;
    }

    public Item copy() {
        return new Item(name, type, value, shopPrice, rarity, description);
    }

    @Override
    public String toString() {
        return String.format("%s [%s] (%s)", name, rarity, type);
    }
}