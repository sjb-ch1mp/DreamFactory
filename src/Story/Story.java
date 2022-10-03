package Story;

import java.util.HashMap;

/**
 * This is the overarching class that stores all rooms, containers, items, enemies,
 * characters (NPC's) and passages in HashMaps.
 * Additional methods are added to get and set individual Story objects within the HashMaps.
 *
 * @author Samuel Brookes
 * @author Shafin Kamal
 */
public class Story  implements java.io.Serializable{

    // Variables
    HashMap<Integer, Room> rooms;
    HashMap<Integer, Container> containers;
    HashMap<Integer, Item> items;
    HashMap<Integer, Enemy> enemies;
    HashMap<Integer, NonPlayerCharacter> nonPlayerCharacters;
    HashMap<Integer, Passage> passages;
    private String name;
    private String introduction;
    private int initialHeroHealth;
    private int initialHeroAttackPower;
    private int initialHeroDefence;

    //Constructor
    public Story(
            String name,
            String introduction,
            int initialHeroHealth,
            int initialHeroAttackPower,
            int initialHeroDefence,
            HashMap<Integer, Room> rooms, 
            HashMap<Integer, Container> containers, 
            HashMap<Integer, Item> items, 
            HashMap<Integer, Enemy> enemies, 
            HashMap<Integer, NonPlayerCharacter> nonPlayerCharacters, 
            HashMap<Integer, Passage> passages
        ) {
        this.name = name;
        this.introduction = introduction;
        this.initialHeroHealth = initialHeroHealth;
        this.initialHeroAttackPower = initialHeroAttackPower;
        this.initialHeroDefence = initialHeroDefence;

        this.rooms = rooms;
        this.containers = containers;
        this.items = items;
        this.enemies = enemies;
        this.nonPlayerCharacters = nonPlayerCharacters;
        this.passages = passages;
    }

    /**
     * Get the name of the story
     *
     * @author Shafin Kamal
     * @return the story name
     */
    public String getName(){
        return name;
    }

    /**
     * Get the story introductions
     *
     * @author Shafin Kamal
     * @return the story introduction
     */
    public String getIntroduction(){
        return introduction;
    }

    /**
     * Get the intial hero health
     *
     * @author Samuel Brookes
     * @return int initial health
     */
    public int getInitialHeroHealth(){
        return initialHeroHealth;
    }

    /**
     * Get initial hero attack power
     *
     * @author Shafin Kamal
     * @return int initial hero attack power
     */
    public int getInitialHeroAttackPower(){
        return initialHeroAttackPower;
    }

    /**
     * Get initial hero health
     *
     * @author Shafin Kamal
     * @return int initial hero health
     */
    public int getInitialHeroDefence(){
        return initialHeroDefence;
    }

    /**
     * if room exists, returns the room at this index in the Story's HashMap of Rooms.
     *
     * @author Samuel Brookes
     * @param roomIndex - index of the Room.
     * @return Room
     */
    public Room getRoom(int roomIndex) {
        return this.rooms.get(roomIndex);
    }

    /**
     * Adds this Room to the HashMap of Rooms in this Story.
     *
     * @author Shafin Kamal
     * @param room - the Room to add.
     */
    public void addRoom(Room room) {
        this.rooms.put(room.roomIndex, room);
    }

    /**
     * if Container exists, returns the Container at this index in the Story's HashMap of Containers.
     *
     * @author Shafin Kamal
     * @param containerIndex - Index of the Container
     * @return Container
     */
    public Container getContainer(int containerIndex) {
        return this.containers.get(containerIndex);
    }

    /**
     * Adds this Container to the HashMap of Containers in this Story.
     *
     * @author Shafin Kamal
     * @param container - the Container to Add
     */
    public void addContainer(Container container) {
        this.containers.put(container.containerIndex, container);
    }

    /**
     * If Item exists, returns the Item at this index in the Story's HashMap of Items.
     *
     * @author Shafin Kamal
     * @param itemIndex - Index of the Item
     * @return Item
     */
    public Item getItem(int itemIndex) {
        return this.items.get(itemIndex);
    }

    /**
     * Adds this Item to the HashMap of Items in this Story
     *
     * @author Shafin Kamal
     * @param item - the Item to add.
     */
    public void addItem(Item item) {
        this.items.put(item.index, item);
    }

    /**
     * If Enemy exists, returns the Enemy at this index in the Story's HashMap of Enemies.
     *
     * @author Shafin Kamal
     * @param enemyIndex -
     * @return Enemy
     */
    public Enemy getEnemy(int enemyIndex) {
        return this.enemies.get(enemyIndex);
    }

    /**
     * Adds this Enemy to the HashMap of Enemies in this Story.
     *
     * @author Shafin Kamal
     * @param enemy enemy
     */
    public void addEnemy(Enemy enemy) {
        this.enemies.put(enemy.enemyIndex, enemy);
    }

    /**
     * Method: getNonPlayerCharacter(int npcIndex)
     * If exists, returns the NonPlayerCharacter that exists at this index within the HashMap of NPC's in the
     * Story class.
     *
     * @author Shafin Kamal
     * @param npcIndex npcIndex
     * @return NonPlayerCharacter
     */
    public NonPlayerCharacter getNonPlayerCharacter(int npcIndex) {
        return this.nonPlayerCharacters.get(npcIndex);
    }

    /**
     * Method: addNonPlayerCharacter
     * Adds this character to the HashMap of NonPlayerCharacters in the Story class.
     *
     * @author Shafin Kamal
     * @param npc npc
     */
    public void addNonPlayerCharacter(NonPlayerCharacter npc) {
        this.nonPlayerCharacters.put(npc.characterIndex, npc);
    }

    /**
     * If Passage exists, returns the Passage at this index in the Story's HashMap of Passages.
     *
     * @author Shafin Kamal
     * @param passageIndex passageIndex
     * @return Passage
     */
    public Passage getPassage(int passageIndex) {
        return this.passages.get(passageIndex);
    }

    /**
     * Adds this Passage (value) and the direction required to travel this Passage (key) to the Story's HashMap
     * of Passages.
     *
     * @author Shafin Kamal
     * @param passageIndex -
     * @param passage -
     */
    public void addPassage(Integer passageIndex, Passage passage) {
        this.passages.put(passageIndex, passage);
    }

    // ** Standard getters and setters ** //

    /**
     * Get the rooms in the story
     *
     * @author Shafin Kamal
     * @return HashMap of rooms
     */
    public HashMap<Integer, Room> getRooms() {
        return rooms;
    }

    /**
     * Set the rooms in the story in a HashMap
     *
     * @author Shafin Kamal
     * @param rooms rooms
     */
    public void setRooms(HashMap<Integer, Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * get containers in the story
     *
     * @author Shafin kamal
     * @return containers
     */
    public HashMap<Integer, Container> getContainers() {
        return containers;
    }

    /**
     * Set containers in the story
     *
     * @author Shafin Kamal
     * @param containers containers HashMap
     */
    public void setContainers(HashMap<Integer, Container> containers) {
        this.containers = containers;
    }

    /**
     * get the items in the story in hashMap
     *
     * @author Shafin Kamal
     * @return hashmap of items
     */
    public HashMap<Integer, Item> getItems() {
        return items;
    }

    /**
     * Set the hashmap of items in the story
     *
     * @author Shafin Kamal
     * @param items hashmap
     */
    public void setItems(HashMap<Integer, Item> items) {
        this.items = items;
    }

    /**
     * Get the hashmap of enemies
     *
     * @author Shafin Kamal
     * @return hashmap of enemies
     */
    public HashMap<Integer, Enemy> getEnemies() {
        return enemies;
    }

    /**
     * set the enemies hashmap of the story
     *
     * @author Shafin Kamal
     * @param enemies enemies hashmap
     */
    public void setEnemies(HashMap<Integer, Enemy> enemies) {
        this.enemies = enemies;
    }

    /**
     * get NPC hashmap of the story
     *
     * @author Shafin Kamal
     * @return hashmap of NPC's in the story
     */
    public HashMap<Integer, NonPlayerCharacter> getNonPlayerCharacters() {
        return nonPlayerCharacters;
    }

    /**
     * set the hashmap of NPC's in the story
     *
     * @author Shafin Kamal
     * @param nonPlayerCharacters npc hashmap
     */
    public void setNonPlayerCharacters(HashMap<Integer, NonPlayerCharacter> nonPlayerCharacters) {
        this.nonPlayerCharacters = nonPlayerCharacters;
    }

    /**
     * get the hashmap of passages in the story
     *
     * @author Shafin Kamal
     * @return the hashmap of passages
     */
    public HashMap<Integer, Passage> getPassages() {
        return passages;
    }

    /**
     * Set the hashmap of passages in the story
     *
     * @author Shafin Kamal
     * @param passages passages hashmap in the story
     */
    public void setPassages(HashMap<Integer, Passage> passages) {
        this.passages = passages;
    }
}
