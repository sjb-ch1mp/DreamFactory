package Story;

/**
 * The Passage Class is what connects one Room to another.
 * Passages can be:
 *
 * > NONE - the user can use a Direction command to traverse this
 * passage
 *
 * > LOCKED - the passage requires some action or item to be UNLOCKED
 *
 * > FREE - the user can traverse this passage to go to another Room
 *
 * > HIDDEN - the user needs a special key or phrase to be unlocked.
 *
 * @author Shafin Kamal
 */
public class Passage  implements java.io.Serializable{

    // Variables
    int passageIndex;
    PassageType passageType;
    String descriptionLocked;
    String descriptionUnlocked;
    boolean isLocked;
    String key;
    int roomIndex;
    String descriptionAction;

    // Constructor for when Passage objects are NOT of type PASSAGE_NONE
    public Passage(int passageIndex, PassageType passageType, String descriptionLocked, String descriptionUnlocked,
                   boolean isLocked, String key, int roomIndex, String descriptionAction) {
        this.passageIndex = passageIndex;
        this.passageType = passageType;
        this.descriptionLocked = descriptionLocked;
        this.descriptionUnlocked = descriptionUnlocked;
        this.isLocked = isLocked;
        this.key = key;
        if (this.passageType != PassageType.PASSAGE_NONE) {
            this.roomIndex = roomIndex;
        }
        this.descriptionAction = descriptionAction;
    }

    // Constructor for when Passage objects are of type PASSAGE_NONE
    public Passage(int passageIndex, PassageType passageType, String descriptionLocked,
                   String descriptionUnlocked, boolean isLocked, String key, String descriptionAction) {
        this.passageIndex = passageIndex;
        this.passageType = passageType;
        this.descriptionLocked = descriptionLocked;
        this.descriptionUnlocked = descriptionUnlocked;
        this.isLocked = isLocked;
        this.key = key;
        this.descriptionAction = descriptionAction;
    }

    // Standard getters and setters

    /**
     * Get the index of the passage
     *
     * @author Shafin Kamal
     * @return the int that denotes the index of the passage
     */
    public int getPassageIndex() {
        return passageIndex;
    }

    /**
     * Set the passage index
     *
     * @author Shafin Kamal
     * @param passageIndex passage index
     */
    public void setPassageIndex(int passageIndex) {
        this.passageIndex = passageIndex;
    }

    /**
     * get the passage type of this passage.
     *
     * @author Shafin Kamal
     * @return passage type
     */
    public PassageType getPassageType() {
        return passageType;
    }

    /**
     * set passage type of this passage
     *
     * @author Shafin Kamal
     * @param passageType passage type
     */
    public void setPassageType(PassageType passageType) {
        this.passageType = passageType;
    }

    /**
     * get the description of the passaged when LOCKED
     *
     * @author Shafin Kamal
     * @return Locked description of passage
     */
    public String getDescriptionLocked() {
        return descriptionLocked;
    }

    /**
     * Set the LOCKED description of this passage
     *
     * @author Shafin Kamal
     * @param descriptionLocked description when locked
     */
    public void setDescriptionLocked(String descriptionLocked) {
        this.descriptionLocked = descriptionLocked;
    }

    /**
     * get the description when the passaged is unlocked
     *
     * @author Shafin Kamal
     * @return unlocked description of the passage
     */
    public String getDescriptionUnlocked() {
        return descriptionUnlocked;
    }

    /**
     * Set the description when passage is unlocked
     *
     * @author Shafin Kamal
     * @param descriptionUnlocked passage unlocked description
     */
    public void setDescriptionUnlocked(String descriptionUnlocked) {
        this.descriptionUnlocked = descriptionUnlocked;
    }

    /**
     * Check if passage is locked
     *
     * @author Shafin Kamal
     * @return true if the passage should be locked, else false.
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * set whether the passage should be locked or not.
     *
     * @author Shafin Kamal
     * @param locked true if the passage should be locked, else false.
     */
    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    /**
     * get the key of this passage that will unlock it
     *
     * @author Shafin Kamal
     * @return the key that will unlock passage
     */
    public String getKey() {
        return key;
    }

    /**
     * Set the key that will unlock the passage
     *
     * @author Shafin Kamal
     * @param key the key that will unlock the passage.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Get the index of the room
     *
     * @author Shafin Kamal
     * @return room index
     */
    public int getRoomIndex() {
        return roomIndex;
    }

    /**
     * Set the index of the room
     *
     * @author Shafin Kamal
     * @param roomIndex room index
     */
    public void setRoomIndex(int roomIndex) {
        this.roomIndex = roomIndex;
    }

    /**
     * Get the description action of the passage
     *
     * @author Shafin Kamal
     * @return the action description
     */
    public String getDescriptionAction() {
        return descriptionAction;
    }

    /**
     * set the description action
     *
     * @author Shafin Kamal
     * @param descriptionAction
     */
    public void setDescriptionAction(String descriptionAction) {
        this.descriptionAction = descriptionAction;
    }

    public enum PassageType {
        // Passage is not visible to player unless unlocked via secret command
        PASSAGE_HIDDEN,

        // Passage is visible to player but player can only pass through the passage if unlocked with correct command.
        // i.e. "USE GOLDEN KEY"
        PASSAGE_LOCKED,

        // Player can freely pass through this passage either through design or unlocked previously.
        PASSAGE_FREE,

        // Player CANNOT pass through. e.g. a wall.
        // Use for when invalid input is given by player.
        PASSAGE_NONE

    }
}
