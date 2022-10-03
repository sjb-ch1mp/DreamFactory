package Story;

import java.util.HashMap;

/**
 * This class pertains to the Hero the user will embody when in Mode.COMBAT
 *
 * @author Shafin Kamal
 * @author Samuel Brookes
 */
public class Hero  implements java.io.Serializable{

    // Variables
    int health;
    int maxHealth;
    int attackPower;
    int defense;
    HashMap<Integer, Boolean> inventory;

    // Constructor
    public Hero(int health, int attackPower, int defense, HashMap<Integer, Boolean> inventory) {
        this.health = health;
        this.maxHealth = health;
        this.attackPower = attackPower;
        this.defense = defense;
        this.inventory = inventory;
    }

    // Standard getters and setters

    /**
     * Get the health of the Hero
     *
     * @author Shafin Kamal
     * @return the health of the Hero
     */
    public int getHealth() {
        return health;
    }

    /**
     * Set the health of the hero.
     *
     * @author Samuel Brookes
     * @param health the int value to set the hero health to. If the value is greater than the maxHealth of the hero,
     *               inputted value is overridden by maxHealth.
     */
    public void setHealth(int health) {
        this.health = health > maxHealth ? maxHealth : (health < 0 ? 0 : health);
    }

    /**
     * Get the attack power of the Hero
     *
     * @author Shafin Kamal
     * @return the attack power
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * Set the attack power of the hero
     *
     * @author Shafin Kamal
     * @param attackPower int to set attack power to
     */
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    /**
     * Get defense of hero.
     *
     * @author Shafin Kamal
     * @return the defense of hero
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Set the defense of the hero
     *
     * @author Shafin Kamal
     * @param defense int to set defense to.
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }

    /**
     * Get the inventory of the hero
     *
     * @author Shafin Kamal
     * @return hero inventory
     */
    public HashMap<Integer, Boolean> getInventory() {
        return inventory;
    }

    /**
     * Set the inventory of the hero
     *
     * @author Shafin Kamal
     * @param inventory HashMap<Integer, Boolean>
     */
    public void setInventory(HashMap<Integer, Boolean> inventory) {
        this.inventory = inventory;
    }
}
