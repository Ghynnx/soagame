package soa.soagame;

public class StoryManager {
    public void intro() {
        sectionHeader("Welcome to Sword Art Online");
        System.out.println("  A surge of vertigo passes through you as you open your eyes to a sky of impossible blue.");
        System.out.println("  Thousands of players, like you, mill about in the vast Town of Beginnings.");
        System.out.println("  Suddenly, the sky flickers. A red cloak appears: a system message from the game's creator.");
        System.out.println("  \"Welcome to Aincrad. You cannot log out. If your HP reaches zero, your body in the real world will die.");
        System.out.println("   The only way to escape is to ascend to the top floor and defeat every guardian.\"");
        System.out.println("  You clench your fist. This is no longer just a game. Your quest for freedom begins.");
        sectionDivider();
    }

    public void afterIntro(String name, String playerClass) {
        System.out.println("  A message appears: \"Player registered as " + name + ", class: " + playerClass.toUpperCase() + ".\"");
        System.out.println("  You feel a faint pulse as your stats and skills are initialized.");
        sectionDivider();
    }

    public void sectionHeader(String title) {
        System.out.println("\n==============================");
        System.out.println("  " + title.toUpperCase());
        System.out.println("==============================\n");
    }

    public static void sectionHeaderStatic(String title) {
        System.out.println("\n==============================");
        System.out.println("  " + title.toUpperCase());
        System.out.println("==============================\n");
    }

    public static void sectionDivider() {
        System.out.println("------------------------------\n");
    }

    public void floorIntro(int floorNum) {
        switch (floorNum) {
            case 1:
                sectionHeader("Floor 1 - Awakening Fields");
                System.out.println("  You step through a shimmering gate. The grass sways, but danger lurks in the shadows.");
                System.out.println("  A distant roar echoes. This is your first real battle as a resident of Aincrad...");
                break;
            case 2:
                sectionHeader("Floor 2 - Feral Wilds");
                System.out.println("  You emerge into tangled woods, where the sunlight barely breaks through. The air is thick with the scent of beasts.");
                System.out.println("  The challenge is real. Only the brave or foolish venture this far.");
                break;
            case 3:
                sectionHeader("Floor 3 - Orc Bastion");
                System.out.println("  A stone fortress rises on the horizon. The ground shakes with orc patrols.");
                System.out.println("  You steel yourself for battle. Only teamwork and courage will see you through.");
                break;
            default:
                sectionHeader("Unknown Floor");
                System.out.println("  You ascend ever higher...");
        }
        sectionDivider();
    }

    public void bossDefeat(int floorNum, String title) {
        sectionHeader("Boss Defeated!");
        switch (floorNum) {
            case 1:
                System.out.println("  The field boss collapses. Players rush to activate the teleport gate.");
                System.out.println("  A wave of relief sweeps through the survivors. The path upward is open.");
                break;
            case 2:
                System.out.println("  The beast howls its last. For a moment, the wilds are silent.");
                System.out.println("  New alliances form as players realize: only by working together can they survive.");
                break;
            case 3:
                System.out.println("  The orc warlord falls. The fortress gates swing open.");
                System.out.println("  Cheers erupt as the next floor is unlocked. You are one step closer to freedom.");
                break;
            default:
                System.out.println("  The guardian is defeated. The way forward is clear.");
        }
        sectionDivider();
    }

    public void saveNotice() {
        System.out.println("  [Save feature not implemented in this demo.]");
        sectionDivider();
    }
}