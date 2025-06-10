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

public class Inventory {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) { items.add(item); }
    public void removeItem(int idx) { items.remove(idx); }
    public Item getItem(int idx) { return items.get(idx); }
    public int size() { return items.size(); }
    public boolean isEmpty() { return items.isEmpty(); }
    public void clear() { items.clear(); }

    public void showInventory() {
        if (items.isEmpty()) {
            System.out.println("Inventory is empty!");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, items.get(i));
        }
    }
}