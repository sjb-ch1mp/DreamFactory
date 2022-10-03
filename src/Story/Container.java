package Story;

import java.io.Serializable;

/**
 * This is a core class that pertains objects that can be searched by the user; known as 'containers'. An example of
 * this would be a closet in a Story.Room that is locked and requires a key to open it and inside there is a
 * Story.Item that can be used in later rooms. The closet in this example is the Container.
 *
 * @author Shafin Kamal
 */
public class Container  implements Serializable {

    // Variables
    int containerIndex;
    String name;
    String descriptionUnlocked;
    String descriptionLocked;
    boolean isLocked;
    String key;
    int itemIndex;
    boolean isLooted;
    String descriptionAction;

    // Constructor
    public Container (int containerIndex) {
        this.containerIndex = containerIndex;
    }

    // Constructor
    public Container(String name) {
        this.name = name;
    }

    /**
     * Core constructor for the Container object.
     *
     * @author Shafin Kamal
     *
     * @param containerIndex - int identifier to access THIS container in auxillary classes.
     * @param name - Name of the Container
     * @param descriptionUnlocked - The description to display if the container is unlocked.
     * @param descriptionLocked - The description to display of the container is locked.
     * @param isLocked - true if container is locked, false if unlocked.
     * @param key - The string that would unlock the container. E.g. "Dream Amulet".
     * @param itemIndex - The index of the item that exists within the container.
     * @param isLooted - true if user has searched the container (see Story.Command), false otherwise.
     * @param descriptionAction - The decription to display when the user is searching the container.
     */
    public Container(int containerIndex, String name, String descriptionUnlocked, String descriptionLocked,
                     boolean isLocked, String key, int itemIndex, boolean isLooted, String descriptionAction) {
        this.containerIndex = containerIndex;
        this.name = name;
        this.descriptionUnlocked = descriptionUnlocked;
        this.descriptionLocked = descriptionLocked;
        this.isLocked = isLocked;
        this.key = key;
        this.itemIndex = itemIndex;
        this.isLooted = isLooted;
        this.descriptionAction = descriptionAction;
    }

    // Getters and Setters


    /**
     * Get the container index.
     *
     * @author Shafin Kamal
     * @return containerIndex of this Container.
     */
    public int getContainerIndex() {
        return containerIndex;
    }

    /**
     * Set the index of this Container.
     *
     * @author Shafin Kamal
     * @param containerIndex - int value to set the index of this Container to.
     */
    public void setContainerIndex(int containerIndex) {
        this.containerIndex = containerIndex;
    }

    /**
     * Get the name of this Container.
     *
     * @author Shafin Kamal.
     * @return the name of this Container.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the Container
     *
     * @author Shafin Kamal
     * @param name - The name to set the Container to.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the Container when unlocked.
     *
     * @author Shafin Kamal
     * @return The 'unlocked' description of this Container.
     */
    public String getDescriptionUnlocked() {
        return descriptionUnlocked;
    }

    /**
     * Set the description of the Container when it is unlocked
     *
     * @author Shafin Kamal
     * @param descriptionUnlocked - (String) The description of the unlocked Container.
     */
    public void setDescriptionUnlocked(String descriptionUnlocked) {
        this.descriptionUnlocked = descriptionUnlocked;
    }

    /**
     * Get the description of the Container when locked.
     *
     * @author Shafin Kamal
     * @return the 'locked' description of this Container.
     */
    public String getDescriptionLocked() {
        return descriptionLocked;
    }

    /**
     * Set the description of the Container when it is locked
     *
     * @author Shafin Kamal
     * @param descriptionLocked - (String) The description of the locked Container.
     */
    public void setDescriptionLocked(String descriptionLocked) {
        this.descriptionLocked = descriptionLocked;
    }

    /**
     * Check if this container is locked or not.
     *
     * @author Shafin Kamal
     * @return true if the container is locked, false if the container is unlocked.
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Set this container to be locked or unlocked.
     *
     * @author Shafin Kamal
     * @param locked true if the container should be locked, false if the container should be unlocked.
     */
    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    /**
     * Get the key that unlocks the container
     *
     * @author Shafin Kamal
     * @return (String) the key that would unlock the container if the Story.Command is valid. e.g. "Dream Amulet"
     */
    public String getKey() {
        return key;
    }

    /**
     * Set the key that will unlock the container.
     *
     * @author Shafin Kamal
     * @param key (String) the key that will unlock the container
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Get the index of the Story.Item that exists in the Container.
     * @return
     */
    public int getItemIndex() {
        return itemIndex;
    }

    /**
     * Set the index of the Item in the container
     *
     * @author Shafin Kamal
     * @param itemIndex index of the item in the container.
     */
    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    /**
     * Check if the container is searched.
     *
     * @author Shafin Kamal
     * @return true if user has searched it, false otherwise.
     */
    public boolean isLooted() {
        return isLooted;
    }

    /**
     * Set this container to be looted or not.
     *
     * @author Shafin Kamal
     * @param looted true if it has been looted, else false.
     */
    public void setLooted(boolean looted) {
        isLooted = looted;
    }

    /**
     * Get the description to display when user is searching the container
     *
     * @author Shafin Kamal
     * @return the description of the container while being looted.
     */
    public String getDescriptionAction() {
        return descriptionAction;
    }

    /**
     * Set the description to display when user is looting the container.
     *
     * @author Shafin Kamal
     * @param descriptionAction (String) description to display when container is being looted.
     */
    public void setDescriptionAction(String descriptionAction) {
        this.descriptionAction = descriptionAction;
    }
}
