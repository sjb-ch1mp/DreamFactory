package Story;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Room Class that stores all information pertaining NPC's, passages, enemies and containers within this Room.
 * It also contains its own name, index and description
 *
 * @author Shafin Kamal
 * @author Samuel Brookes
 */
public class Room implements java.io.Serializable{

    // Variables

    // the index associated with THIS room
    int roomIndex;

    // Name of the room
    String roomName;

    // A narrative description of the room and its setting.
    String description;

    // Data structure to store characters, passages, enemies and containers.
    ArrayList<Integer> nonPlayerCharacters;
    HashMap<Direction, Integer> passages;
    ArrayList<Integer> enemies;
    ArrayList<Integer> containers;

    //Last room in the story
    boolean isLastRoom;

    // Constructor
    public Room(int roomIndex, String roomName, String description, ArrayList<Integer> nonPlayerCharacters,
                HashMap<Direction, Integer> passages, ArrayList<Integer> enemies, ArrayList<Integer> containers) {
        this.roomIndex = roomIndex;
        this.roomName = roomName;
        this.description = description;
        this.nonPlayerCharacters = nonPlayerCharacters;
        this.passages = passages;
        this.enemies = enemies;
        this.containers = containers;

        //isLastRoom is set to false by default - it must be set by the StoryParser on the last room of the story by
        // calling the function setLastRoom
        this.isLastRoom = false;
    }

    /**
     * Set this room to the last room
     *
     * @author Samuel Brookes
     */
    public void makeLastRoom(){
        isLastRoom = true;
    }

    /**
     * Check if this room is the last one or not.
     *
     * @author Samuel Brookes
     * @return true if this room is the last one, else false.
     */
    public boolean isLastRoom(){
        return isLastRoom;
    }

    /**
     * Get room index
     *
     * @author Shafin Kamal
     * @return room index int
     */
    public int getRoomIndex() {
        return roomIndex;
    }

    /**
     * Set room index
     *
     * @author Shafin Kamal
     * @param roomIndex room index int
     */
    public void setRoomIndex(int roomIndex) {
        this.roomIndex = roomIndex;
    }

    /**
     * Get this room name
     *
     * @author Shafin Kamal
     * @return room name
     */
    public String getRoomName() {
        return roomName;
    }


    /**
     * set the room name
     *
     * @author Shafin Kamal
     * @param roomName room name
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * get description of the room
     *
     * @author Shafin Kamal
     * @return the description of the room
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set room description
     *
     * @author Shafin Kamal
     * @param description room description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get the ArrayList of NPC's (ArrayList of the NPC indexes)
     *
     * @author Shafin Kamal
     * @return ArrayList of NPC indices
     */
    public ArrayList<Integer> getNonPlayerCharacters() {
        return nonPlayerCharacters;
    }

    /**
     * Set the arrayList of NPC indices.
     *
     * @author Shafin Kamal
     * @param nonPlayerCharacters arrayList<Integer> npcs
     */
    public void setNonPlayerCharacters(ArrayList<Integer> nonPlayerCharacters) {
        this.nonPlayerCharacters = nonPlayerCharacters;
    }

    /**
     * Return the passages in this room
     *
     * @author Shafin Kamal
     * @return HashMap of passages with key Direction and value Passage index
     */
    public HashMap<Direction, Integer> getPassages() {
        return passages;
    }

    /**
     * Set the HashMap of Passages
     *
     * @author Shafin Kamal
     * @param passages HashMap<Direction, Integer> passages
     */
    public void setPassages(HashMap<Direction, Integer> passages) {
        this.passages = passages;
    }

    /**
     * get the enemies that exist in this room
     *
     * @author Shafin Kamal
     * @return arraylist of enemy indices that exist in the room
     */
    public ArrayList<Integer> getEnemies() {
        return enemies;
    }

    /**
     * set the arraylist of enemies pertaining to this room
     *
     * @author Shafin Kamal
     * @param enemies arraylist of Integer
     */
    public void setEnemies(ArrayList<Integer> enemies) {
        this.enemies = enemies;
    }

    /**
     * Get the arrayList of containers in this room
     *
     * @author Shafin Kamal
     * @return ArrayList <Integer>
     */
    public ArrayList<Integer> getContainers() {
        return containers;
    }

    /**
     * Set the arrayList of Containers in this room
     *
     * @author Shafin Kamal
     * @param containers arrayList<ContainerIndex>
     */
    public void setContainers(ArrayList<Integer> containers) {
        this.containers = containers;
    }
}

