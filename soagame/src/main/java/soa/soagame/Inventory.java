package soa.soagame;

import java.util.*;

public class Inventory {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) { items.add(item); }

    public void removeItem(int idx) {
        if (idx >= 0 && idx < items.size()) items.remove(idx);
    }

    public Item getItem(int idx) {
        if (idx >= 0 && idx < items.size()) return items.get(idx);
        return null;
    }

    public int size() { return items.size(); }

    public boolean isEmpty() { return items.isEmpty(); }

    public void clear() { items.clear(); }

    public void showInventory() {
        if (items.isEmpty()) {
            System.out.println("  [Inventory is empty!]");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("  %2d. %s\n", i + 1, items.get(i));
        }
    }
}