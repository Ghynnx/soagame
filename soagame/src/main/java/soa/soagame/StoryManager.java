/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soa.soagame;

/**
 *
 * @author Student's Account
 */
public class StoryManager {
    public void intro() {
        System.out.println(
            "\nYou awaken in the Town of Beginnings, deep within the floating fortress of Aincrad.\n" +
            "A voice booms from the sky: \"Welcome, players. You are now part of this world. Defeat each floor's guardian to advance. Death here is real.\"\n" +
            "Panic and disbelief sweep through the crowd, but determination soon follows. You resolve to fight your way to freedom, one floor at a time..."
        );
    }

    public void onBossDefeat(int dungeonNumber) {
        switch (dungeonNumber) {
            case 1:
                System.out.println(
                    "\n--- Floor 1: The Awakening ---\n" +
                    "You have slain the Goblin Chieftain, breaking the barrier to the next floor.\n" +
                    "Word spreads quickly; the first teleport gate opens, offering hope to the trapped players.\n" +
                    "The Town of Beginnings fills with new ambition as the path upward is revealed."
                );
                break;
            case 2:
                System.out.println(
                    "\n--- Floor 2: The Feral Wilds ---\n" +
                    "The Wolf Pack Alphas have fallen. The forests of Floor 2 are tamed, if only for a moment.\n" +
                    "A bustling settlement forms around the second gate, with players setting up shops and forming groups.\n" +
                    "Rumors emerge of hidden quests and secret treasures scattered through the wilds."
                );
                break;
            case 3:
                System.out.println(
                    "\n--- Floor 3: The Orcish Bastion ---\n" +
                    "You breach the orc fortress, toppling its warlord in a fierce battle.\n" +
                    "The first player guilds are founded, offering protection and camaraderie.\n" +
                    "Stories of betrayal and heroism begin to circulate; trust becomes a precious commodity."
                );
                break;
            case 4:
                System.out.println(
                    "\n--- Floor 4: The Ruined Halls ---\n" +
                    "Defeating the Troll King, you open the ancient teleport chamber.\n" +
                    "Players discover the ruins are filled with traps and puzzles, requiring cooperation to survive.\n" +
                    "A player-run market flourishes, and competition for rare loot intensifies."
                );
                break;
            case 5:
                System.out.println(
                    "\n--- Floor 5: The Iron Citadel ---\n" +
                    "The Ogre Warlord falls, but at great cost. Many players mourn fallen friends.\n" +
                    "A memorial is built in the Citadel, and a new alliance of guilds is formed to lead the charge upward.\n" +
                    "Some players retreat to safer floors, while others vow to press on, no matter the danger."
                );
                break;
            case 6:
                System.out.println(
                    "\n--- Floor 6: The Dragon’s Lair ---\n" +
                    "You have vanquished the dragon, earning legendary renown among all players.\n" +
                    "For the first time, the floating city above is visible—proof that your journey can continue.\n" +
                    "A festival erupts in the main town, but there is talk of even greater perils on the horizons above."
                );
                break;
            default:
                System.out.println(
                    "\n--- Floor " + dungeonNumber + " Cleared ---\n" +
                    "You have conquered another guardian and advanced further into Aincrad’s depths.\n" +
                    "Each victory brings both hope and uncertainty. The next floor awaits, shrouded in mystery."
                );
        }
    }
}
