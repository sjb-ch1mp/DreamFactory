package Story;

/**
 * This class pertains to the Enemy within the story which the user (Hero) can combat with.
 *
 * @author Shafin Kamal
 * @author Samuel Brookes
 */
public class Enemy implements java.io.Serializable {

    // Variables
    int enemyIndex;
    String name;
    // If health <= 0, enemy dead.
    String descriptionDead;
    String descriptionAlive;
    int health;
    int attackPower;
    int defence;
    // enemy defeat reward
    int itemIndex;
    boolean hasLoot;


    // Constructor
    public Enemy(int enemyIndex, String name, String descriptionDead, String descriptionAlive, int health, int attackPower, int defence, int itemIndex, boolean hasLoot) {
        this.enemyIndex = enemyIndex;
        this.name = name;
        this.descriptionDead = descriptionDead;
        this.descriptionAlive = descriptionAlive;
        this.health = health;
        this.attackPower = attackPower;
        this.defence = defence;
        this.itemIndex = itemIndex;
        this.hasLoot = hasLoot;
    }

    /**
     * Check if the enemy drops loot after its defeat.
     *
     * @author Samuel Brookes
     * @return true if loot should be dropped, otherwise false.
     */
    public boolean dropsLoot(){
        return hasLoot;
    }

    /**
     * Get the index of this enemy to access it at other Auxillary classes.
     *
     * @author Shafin Kamal
     * @return the index of this Enemy
     */
    public int getEnemyIndex() {
        return enemyIndex;
    }

    /**
     * Set the index of the enemy
     *
     * @author Shafin Kamal
     * @param enemyIndex the int to set the enemyIndex to.
     */
    public void setEnemyIndex(int enemyIndex) {
        this.enemyIndex = enemyIndex;
    }

    /**
     * Get the name of this Enemy.
     *
     * @author Shafin Kamal
     * @return the name of the Enemy.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the enemy
     *
     * @author Shafin Kamal
     * @param name the name of the enemy
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the enemy depending on if they are alive or dead.
     *
     * @author Shafin Kamal
     * @return the description of the enemy
     */
    public String getDescription() {
        if (health <= 0) {
            return this.descriptionDead;
        } else {
            return this.descriptionAlive;
        }
    }

    /**
     * Set the description when the enemy is dead
     *
     * @author Shafin Kamal
     * @param descriptionDead the description when enemy is dead.
     */
    public void setDescriptionDead(String descriptionDead) {
        this.descriptionDead = descriptionDead;
    }

    /**
     * Set the description when the enemy is dead.
     *
     * @author Shafin Kamal
     * @param descriptionAlive the description when the enemy is alive.
     */
    public void setDescriptionAlive(String descriptionAlive) {
        this.descriptionAlive = descriptionAlive;
    }

    /**
     * Get the health of the enemy.
     *
     * @author Shafin Kamal
     * @return the health of the enemy
     */
    public int getHealth() {
        return health;
    }

    /**
     * Set the health of the enemy
     *
     * @author Shafin Kamal
     * @param health int value of the health of the enemy
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Get the attack power of the enemy
     *
     * @author Shafin Kamal
     * @return the attack power of the enemy
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * Set the attack power of the enemy
     *
     * @author Shafin Kamal
     * @param attackPower int value of the attack power of the enemy
     */
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    /**
     * Get the defense value of the enemy
     *
     * @author Shafin Kamal
     * @return the defense value of the enemy.
     */
    public int getDefence() {
        return defence;
    }

    /**
     * Set the defense value of the enemy.
     *
     * @author Shafin Kamal
     * @param defence int value of the defense of the enemy.
     */
    public void setDefence(int defence) {
        this.defence = defence;
    }

    /**
     * Get the index of the item that the enemy drops when defeated (see boolean hasLoot).
     *
     * @author Shafin Kamal
     * @return the index of the item dropped when enemy is defeated if hasLoot == true.
     */
    public int getItemIndex() {
        return itemIndex;
    }

    /**
     * Set the index of the item that is dropped when enemy is defeated if hasLoot == true.
     *
     * @author Shafin Kamal
     * @param itemIndex the index of the item to drop when enemy is defeated.
     */
    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }
}
