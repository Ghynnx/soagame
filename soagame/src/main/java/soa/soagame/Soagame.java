/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package soa.soagame;

/**
 *
 * @author Student's Account
 */


import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Soagame {
    // --- Game Data for Dungeons and Shop ---
    private static final Dungeon[] DUNGEONS = {
        new Dungeon("E", 1, "Goblin", 80, 8, 14, 4, 30, 10, 6),
        new Dungeon("D", 3, "Wolf", 120, 12, 16, 6, 40, 16, 10),
        new Dungeon("C", 5, "Orc", 180, 16, 20, 9, 60, 24, 15),
        new Dungeon("B", 7, "Troll", 240, 20, 26, 12, 80, 35, 25),
        new Dungeon("A", 10, "Ogre", 300, 26, 34, 16, 100, 50, 40),
        new Dungeon("S", 15, "Dragon", 500, 40, 55, 26, 150, 100, 80)
    };
    private static final Item[] SHOP_ITEMS = {
        new Item("HP Potion", Item.Type.HP_POTION, 30, 10, Item.Rarity.COMMON, "Restores a small amount of HP."),
        new Item("Mana Potion", Item.Type.MANA_POTION, 20, 12, Item.Rarity.COMMON, "Restores a small amount of Mana."),
        new Item("Sword", Item.Type.SWORD, 3, 40, Item.Rarity.UNCOMMON, "A basic sword, increases attack slightly."),
        new Item("Staff", Item.Type.STAFF, 3, 40, Item.Rarity.UNCOMMON, "A staff for mages, increases magic attack."),
        new Item("Shield", Item.Type.SHIELD, 2, 35, Item.Rarity.UNCOMMON, "A basic shield, increases defense."),
        new Item("Helmet", Item.Type.HELMET, 1, 60, Item.Rarity.RARE, "A sturdy helmet, increases defense."),
        new Item("Armor", Item.Type.ARMOR, 2, 80, Item.Rarity.RARE, "Well-crafted armor, increases defense.")
    };
    private static final Item[] ITEM_DROPS = SHOP_ITEMS; // All shop items may drop in battle
    private static final Map<Item.Rarity, Double> RARITY_DROP_CHANCE = Map.of(
        Item.Rarity.COMMON, 0.30,
        Item.Rarity.UNCOMMON, 0.15,
        Item.Rarity.RARE, 0.07
    );
    private static final Map<String, Double> DIFFICULTY = Map.of(
        "easy", 0.8,
        "normal", 1.0,
        "hard", 1.2
    );

    private final Scanner sc = new Scanner(System.in);
    private final Random random = new Random();
    private Player player;
    private final StoryManager story = new StoryManager();

    public static void main(String[] args) {
        new Soagame().run();
    }

    public void run() {
        System.out.println("=== Sword Art Dungeons ===");
        System.out.println("1. New Game\n2. Load Game");
        String choice = sc.nextLine().trim();
        if ("2".equals(choice)) {
            if (!loadProgress()) startNewGame();
        } else {
            startNewGame();
        }
        story.intro();

        while (true) {
            player.restore();
            displayPlayerStatus();
            System.out.println("\nMain Menu: 1.Dungeon 2.Inventory 3.Shop 4.Save 5.Quit");
            String cmd = sc.nextLine().trim();
            switch (cmd) {
                case "1": enterDungeon(); break;
                case "2": inventoryMenu(); break;
                case "3": shopMenu(); break;
                case "4": saveProgress(); break;
                case "5": System.out.println("Thanks for playing!"); return;
                default: System.out.println("Invalid input."); break;
            }
        }
    }

    private void displayPlayerStatus() {
        System.out.printf("\n%s (Lv.%d %s) HP: %d/%d | Mana: %d/%d | EXP: %d/%d | Gold: %d\n",
            player.name, player.level, player.playerClass,
            player.hp, player.maxHp(), player.mana, player.maxMana(),
            player.exp, player.expToLevel, player.gold
        );
        System.out.println("Passive: " + player.passiveSkill);
    }

    private void inventoryMenu() {
        player.inventory.showInventory();
        if (!player.inventory.isEmpty()) {
            System.out.println("Choose item number to use or 0 to cancel:");
            String itemChoice = sc.nextLine().trim();
            try {
                int itemIdx = Integer.parseInt(itemChoice) - 1;
                if (itemIdx >= 0) player.useItem(itemIdx);
            } catch (NumberFormatException ignored) {}
        }
    }

    private void shopMenu() {
        while (true) {
            System.out.println("\n=== Shop ===  (Gold: " + player.gold + ")");
            for (int i = 0; i < SHOP_ITEMS.length; i++) {
                Item it = SHOP_ITEMS[i];
                System.out.printf("%d. %s [%s] - %d gold\n    %s\n", i+1, it.name, it.rarity, it.shopPrice, it.description);
            }
            System.out.println("Enter item number to buy, or 0 to exit shop.");
            String input = sc.nextLine().trim();
            if ("0".equals(input)) break;
            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx < 0 || idx >= SHOP_ITEMS.length) {
                    System.out.println("No such item.");
                } else if (player.gold < SHOP_ITEMS[idx].shopPrice) {
                    System.out.println("You don't have enough gold!");
                } else {
                    player.gold -= SHOP_ITEMS[idx].shopPrice;
                    player.inventory.addItem(SHOP_ITEMS[idx].copy());
                    System.out.println("You purchased " + SHOP_ITEMS[idx].name + "!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
    }

    private void startNewGame() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();
        String cls;
        do {
            System.out.println("Choose your class: warrior, mage, archer, tank");
            cls = sc.nextLine().trim().toLowerCase();
        } while (!Player.START_CLASSES.contains(cls));
        player = new Player(name, cls, story);
        System.out.printf("Welcome %s the %s! Your adventure begins...\n", player.name, player.playerClass);
    }

    private void enterDungeon() {
        showDungeons();
        int idx = -1;
        while (true) {
            System.out.print("Choose dungeon number: ");
            String input = sc.nextLine().trim();
            try {
                idx = Integer.parseInt(input) - 1;
                if (idx < 0 || idx >= DUNGEONS.length) {
                    System.out.println("Invalid input.");
                } else break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
        Dungeon d = DUNGEONS[idx];
        if (player.level < d.reqLevel) {
            System.out.println("Required level: " + d.reqLevel);
            return;
        }
        String diff;
        do {
            System.out.println("Choose difficulty: easy, normal, hard");
            diff = sc.nextLine().trim().toLowerCase();
        } while (!DIFFICULTY.containsKey(diff));
        System.out.printf("Entering Rank %s Dungeon (%s mode)...\n", d.rank, diff);
        fightDungeon(d, diff, idx + 1);
    }

    private void showDungeons() {
        System.out.println("Available dungeons:");
        for (int i = 0; i < DUNGEONS.length; i++) {
            Dungeon d = DUNGEONS[i];
            System.out.printf("%d. Rank %s Dungeon (Level %d+) - %s\n", i + 1, d.rank, d.reqLevel, d.name);
        }
    }

    private void fightDungeon(Dungeon d, String diff, int dungeonNumber) {
        double multi = DIFFICULTY.get(diff);

        for (int stage = 1; stage <= 4; stage++) {
            Enemy enemy;
            if (stage <= 3) {
                System.out.println("\nMinion " + stage + " appears!");
                enemy = new Enemy(
                    d.name + " Minion",
                    (int) (d.baseMinionHp * multi),
                    (int) (d.baseMinionMinAtk * multi),
                    (int) (d.baseMinionMaxAtk * multi),
                    (int) (d.baseMinionDef * multi),
                    d.reqLevel,
                    (int) (d.baseMinionMana * multi),
                    (int) (d.expReward * 0.3),
                    (int) (d.goldReward * 0.2)
                );
            } else {
                System.out.println("\nThe Boss appears: " + d.name + "!");
                enemy = new Enemy(
                    d.name,
                    (int) (d.baseMinionHp * multi * 2),
                    (int) (d.baseMinionMinAtk * multi * 2),
                    (int) (d.baseMinionMaxAtk * multi * 2),
                    (int) (d.baseMinionDef * multi * 2),
                    d.reqLevel,
                    (int) (d.baseMinionMana * multi * 2),
                    d.expReward * 2,
                    d.goldReward * 2
                );
            }
            if (!fightEnemy(enemy)) {
                System.out.println("You were defeated! Returning to town...");
                return;
            }
        }
        story.onBossDefeat(dungeonNumber);
        System.out.println("Dungeon cleared! Congratulations!");
    }

    private boolean fightEnemy(Enemy e) {
        while (player.hp > 0 && e.hp > 0) {
            player.applyPassives();
            if (e.poisonedTurns > 0) {
                System.out.println("Enemy takes poison damage!");
                e.hp -= Math.max(2, player.level * 2);
                e.poisonedTurns--;
            }
            System.out.printf(
                "\nYour HP: %d/%d | Mana: %d/%d\nEnemy: %s (Lv.%d) HP: %d/%d | ATK: %d-%d | DEF: %d\n",
                player.hp, player.maxHp(), player.mana, player.maxMana(),
                e.name, e.level, e.hp, e.maxHp, e.minAtk, e.maxAtk, e.def
            );
            System.out.println("Choose action: 1.Attack 2.Inventory 3.Skill 4.Flee");
            String action = sc.nextLine().trim();
            boolean acted = false;
            switch (action) {
                case "1":
                    player.attack(e);
                    acted = true;
                    break;
                case "2":
                    player.inventory.showInventory();
                    if (!player.inventory.isEmpty()) {
                        System.out.println("Choose item number to use or 0 to cancel:");
                        String itemChoice = sc.nextLine().trim();
                        try {
                            int itemIdx = Integer.parseInt(itemChoice) - 1;
                            if (itemIdx >= 0) player.useItem(itemIdx);
                        } catch (NumberFormatException ignored) {}
                    }
                    break;
                case "3":
                    player.displaySkillMenuAndUse(e, sc);
                    acted = true;
                    break;
                case "4":
                    if (random.nextDouble() < 0.5) {
                        System.out.println("You fled successfully!");
                        return true;
                    } else {
                        System.out.println("Failed to flee!");
                        acted = true;
                    }
                    break;
                default:
                    System.out.println("Invalid input.");
                    break;
            }
            if (acted && e.hp > 0) {
                if (e.stunned) {
                    System.out.println("Enemy is stunned and skips its turn!");
                    e.stunned = false;
                } else {
                    e.attack(player);
                }
            }
            player.decrementCooldowns();
        }
        if (player.hp > 0) {
            System.out.println("You defeated the " + e.name + "!");
            player.gainExp(e.expReward);
            player.gold += e.goldReward;
            System.out.printf("You gained %d EXP and found %d gold.\n", e.expReward, e.goldReward);
            Item item = getRandomDrop();
            if (item != null) {
                player.inventory.addItem(item);
                System.out.println("You found a " + item + "!");
            }
            return true;
        } else {
            System.out.println("You were defeated!");
            int lostGold = Math.max(10, (int) (player.gold * 0.2));
            player.gold = Math.max(0, player.gold - lostGold);
            System.out.println("You dropped " + lostGold + " gold!");
            if (!player.inventory.isEmpty()) {
                int idx = (int) (Math.random() * player.inventory.size());
                Item lost = player.inventory.getItem(idx);
                player.inventory.removeItem(idx);
                System.out.println("You lost your " + lost + "!");
            }
            if (player.level > 1 && Math.random() < 0.2) {
                player.level--;
                player.exp = 0;
                System.out.println("You feel weaker... You lost a level!");
            }
            return false;
        }
    }

    private Item getRandomDrop() {
        double roll = random.nextDouble();
        for (Item.Rarity rarity : List.of(Item.Rarity.RARE, Item.Rarity.UNCOMMON, Item.Rarity.COMMON)) {
            if (roll < RARITY_DROP_CHANCE.get(rarity)) {
                List<Item> candidates = new ArrayList<>();
                for (Item item : ITEM_DROPS) if (item.rarity == rarity) candidates.add(item);
                if (!candidates.isEmpty())
                    return candidates.get(random.nextInt(candidates.size())).copy();
            }
        }
        return null;
    }

    // --- Save/Load using FileWriter/FileReader (no Serializable) ---
    public void saveProgress() {
        try (FileWriter fw = new FileWriter("savegame.txt")) {
            fw.write(player.name + "," + player.playerClass + "," + player.level + "," +
                     player.hp + "," + player.mana + "," + player.exp + "," +
                     player.expToLevel + "," + player.gold + "\n");
            fw.write("" + player.inventory.size() + "\n");
            for (int i = 0; i < player.inventory.size(); i++) {
                Item item = player.inventory.getItem(i);
                fw.write(item.name + "," + item.type + "," + item.value + "," +
                         item.shopPrice + "," + item.rarity + "," +
                         item.description.replace(",", ";") + "\n");
            }
            System.out.println("Progress saved!");
        } catch (IOException e) {
            System.out.println("Failed to save progress: " + e.getMessage());
        }
    }

    public boolean loadProgress() {
        try (FileReader fr = new FileReader("savegame.txt")) {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = fr.read()) != -1) sb.append((char) c);
            String[] lines = sb.toString().split("\n");
            if (lines.length < 2) throw new IOException("Corrupt save file.");
            String[] stats = lines[0].split(",");
            String name = stats[0];
            String playerClass = stats[1];
            player = new Player(name, playerClass, story);
            player.level = Integer.parseInt(stats[2]);
            player.hp = Integer.parseInt(stats[3]);
            player.mana = Integer.parseInt(stats[4]);
            player.exp = Integer.parseInt(stats[5]);
            player.expToLevel = Integer.parseInt(stats[6]);
            player.gold = Integer.parseInt(stats[7]);
            int invSize = Integer.parseInt(lines[1].trim());
            player.inventory.clear();
            for (int i = 0; i < invSize; i++) {
                String[] itemData = lines[2 + i].split(",", 6);
                String itemName = itemData[0];
                Item.Type type = Item.Type.valueOf(itemData[1]);
                int value = Integer.parseInt(itemData[2]);
                int shopPrice = Integer.parseInt(itemData[3]);
                Item.Rarity rarity = Item.Rarity.valueOf(itemData[4]);
                String desc = itemData[5].replace(";", ",");
                player.inventory.addItem(new Item(itemName, type, value, shopPrice, rarity, desc));
            }
            System.out.println("Progress loaded! Welcome back, " + player.name + " the " + player.playerClass + "!");
            return true;
        } catch (Exception e) {
            System.out.println("No savegame found or failed to load.");
            return false;
        }
    }

    // --- Supporting Classes ---
    public static class Dungeon {
        public final String rank, name;
        public final int reqLevel, baseMinionHp, baseMinionMinAtk, baseMinionMaxAtk, baseMinionDef, baseMinionMana, expReward, goldReward;
        public Dungeon(String rank, int reqLevel, String name, int baseMinionHp, int baseMinionMinAtk, int baseMinionMaxAtk, int baseMinionDef, int baseMinionMana, int expReward, int goldReward) {
            this.rank = rank;
            this.reqLevel = reqLevel;
            this.name = name;
            this.baseMinionHp = baseMinionHp;
            this.baseMinionMinAtk = baseMinionMinAtk;
            this.baseMinionMaxAtk = baseMinionMaxAtk;
            this.baseMinionDef = baseMinionDef;
            this.baseMinionMana = baseMinionMana;
            this.expReward = expReward;
            this.goldReward = goldReward;
        }
    }
}