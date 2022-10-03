package Story;

/**
 * An object that the user can utilise in Combat or Exploration mode.
 *
 * @author Shafin Kamal
 */
public class Item  implements java.io.Serializable{

    // Variables
    int index;
    String name;
    String description;
    boolean isEquippable;
    boolean isConsumable;
    int attackPower;
    int defence;
    int health;
    boolean isEmpty;

    // Constructor
    public Item(int index, String name, String description, boolean isEquippable, int attackPower, int defence) {
        this.index = index;
        this.name = name;
        this.description = description;
        this.isEquippable = isEquippable;
        this.isConsumable = false;
        this.attackPower = attackPower;
        this.defence = defence;
        this.health = 0;
    }

    //Constructor for consumable item
    public Item(int index, String name, String description, int attackPower, int defence, int health){
        this.index = index;
        this.name = name;
        this.description = description;
        this.isEquippable = false;
        this.isConsumable = true;
        this.isEmpty = false;
        this.attackPower = attackPower;
        this.defence = defence;
        this.health = health;
    }

    // Getters and Setters

    /**
     * Get the index of the Item
     *
     * @author Shafin Kamal
     * @return index of item
     */
    public int getIndex() {
        return index;
    }

    /**
     * When Item is consumed, it is now empty.
     *
     * @author Samuel Brookes
     */
    public void useConsumableItem(){
        isEmpty = true;
    }

    /**
     * Check if item is empty
     *
     * @author Shafin Kamal
     * @return true if item is empty, else false.
     */
    public boolean isEmpty(){
        return isEmpty;
    }

    /**
     * Set index of item
     *
     * @author Shafin Kamal
     * @param index set index of item
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * get the name of the item
     *
     * @author Shafin Kamal
     * @return the name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the item
     *
     * @author Shafin Kamal
     * @param name the name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the item
     *
     * @author Shafin Kamal
     * @return the description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the item
     *
     * @author Shafin Kamal
     * @param description The description of the item
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Check if this item is equippable
     *
     * @author Shafin Kamal
     * @return true if the item is equippable, otherwise, false.
     */
    public boolean isEquippable() {
        return isEquippable;
    }

    /**
     * Set whether this item should be equippable or not.
     *
     * @author Shafin Kamal
     * @param equippable true, if the item should be equippable, false if not.
     */
    public void setEquippable(boolean equippable) {
        isEquippable = equippable;
    }

    /**
     * Get the attack power of the item
     *
     * @author Shafin Kamal
     * @return the attack power of the item
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * Set the attack power of the item
     *
     * @author Shafin Kamal
     * @param attackPower the int to set the attack power to.
     */
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    /**
     * get the defense stat of the item
     *
     * @author Shafin Kamal
     * @return the defense of the item
     */
    public int getDefence() {
        return defence;
    }

    /**
     * Set the defense of the item
     *
     * @author Shafin Kamal
     * @param defence the int the defense of the item should be set to.
     */
    public void setDefence(int defence) {
        this.defence = defence;
    }

    /**
     * Get the health stat of the item
     *
     * @author Shafin Kamal
     * @return the health stat of the item
     */
    public int getHealth(){
        return health;
    }

    /**
     * Check if this item is consumable.
     *
     * @author Shafin Kamal
     * @return true if the item is consumable, false otherwise
     */
    public boolean isConsumable(){
        return isConsumable;
    }
}
