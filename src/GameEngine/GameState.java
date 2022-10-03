package GameEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Story.Hero;
import Story.Item;
import Story.Mode;
import Story.Passage;
import Story.Room;
import Story.Story;

/**
 * The GameState class maintains the state for the game as the player
 * progress through the story. This includes - the hero stats, the game mode,
 * the state of combatants during combat, the history of the game, and the current room.
 * 
 * @author Samuel J. Brookes (u5380100)
 */
public class GameState implements Serializable {
    
    private Hero hero;
    private Story story;
    private GameHistory gameHistory;
    private Mode currentMode;
    private int currentRoomIndex;
    private Arena arena;
    
    /**
     * The constructor for the GameState class requires a Hero
     * and the Story. 
     * 
     * @param hero (Hero) the hero that will be the avatar throughout the game
     * @param story (Story) the Story that the hero will play
     */
    public GameState(Hero hero, Story story){
        this.hero = hero;
        this.story = story;

        gameHistory = new GameHistory();
        currentRoomIndex = 0;
        currentMode = Mode.EXPLORATION;
    }

    /**
     * Utility function that retrieves the Story.
     * 
     * @return (Story) the story as it is currently stored in the game state
     */
    public Story getStory(){
        return story;
    }

    /**
     * Utility function to replace the story with a new one. 
     * 
     * @param story (Story) new story
     */
    public void updateStory(Story story){
        this.story = story;
    }
    
    /**
     * The moveToNewRoom() function will change the current room pointer
     * to that which is referenced by the passage that the player moves through.
     * 
     * @param passage (Passage) the passage that the player moved through
     */
    public void moveToNewRoom(Passage passage){
        currentRoomIndex = passage.getRoomIndex(); 
    }

    /**
     * Utility function to retrieve the current room that the player is in.
     * 
     * @return (Room) the room that the player is currently in
     */
    public Room getCurrentRoom(){
        return story.getRoom(currentRoomIndex);
    }

    /**
     * Utility function to change the current room to a new one.
     * 
     * @param room (Room) the new room
     */
    public void updateCurrentRoom(Room room){
        HashMap<Integer, Room> rooms = story.getRooms();
        rooms.put(currentRoomIndex, room);
        story.setRooms(rooms);
    }

    /**
     * Utility function to retrieve the hero from the game state.
     * 
     * @return (Hero) the hero from the game state
     */
    public Hero getHero(){
        return hero;
    }

    /**
     * Utility class to create a list of items in the Hero's inventory
     * that are currently equipped. This allows the Arena class to calculate
     * hero's stats after considering the effect of equipped items. 
     * 
     * @return ArrayList<Item> list of equipped items
     */
    public ArrayList<Item> getEquippedItems(){
        ArrayList<Item> equippedItems = new ArrayList<Item>();
        HashMap<Integer, Boolean> inventory = hero.getInventory();

        for(Map.Entry<Integer, Boolean> entry : inventory.entrySet()){
            if(entry.getValue()){
                equippedItems.add(story.getItem(entry.getKey()));
            }
        }

        return equippedItems;
    }

    /**
     * Utility function to change the hero in the gamestate
     * to a new hero. 
     * 
     * @param hero (Hero) the new hero
     */
    public void updateHero(Hero hero){
        this.hero = hero;
    }

    /**
     * Utility function to retrieve the mode that the game state is
     * currently in. 
     * 
     * @return (Mode) the current game mode
     */
    public Mode getCurrentMode(){
        return currentMode;
    }

    /**
     * Utility function to toggle between Mode.EXPLORATION and Mode.COMBAT
     */
    public void toggleMode(){
        if(currentMode == Mode.EXPLORATION){
            currentMode = Mode.COMBAT;
        }else{
            currentMode = Mode.EXPLORATION;
        }
    }

    /**
     * The gameOver() function changes the current game mode to Mode.GAMEOVER. 
     * This is the signal to the UI that the game is finished.
     * This occurs if (1) the hero is dead, or (2) the hero has reached the final
     * room of the game.
     */
    public void gameOver(){
        currentMode = Mode.GAMEOVER;
    }

    /**
     * Utility function to change the Arena that is stored in the GameState to a 
     * new Arena object. This occurs at the start of combat. 
     * 
     * @param arena (Arena) the new arena
     */
    public void setArena(Arena arena){
        this.arena = arena;
    }

    /**
     * Utility function that retrieves the current Arena stored in the GameState.
     * 
     * @return (Arena) the current arena
     */
    public Arena getArena(){
        return arena;
    }

    /**
     * Utility function that retrieves the GameHistory stored in the GameState.
     * 
     * @return (GameHistory) the game history in the game state
     */
    public GameHistory getGameHistory(){
        return gameHistory;
    }

    /**
     * Utility function to add the last Action and it's corresponding Consequence 
     * to the GameHistory stored in the GameState. This occurs just prior to the 
     * Consequence being passed back to the UI. 
     * 
     * @param action (Action) the last action taken by the player
     * @param consequence (Consequence) it's corresponding consequence.
     */
    public void addActionConsequence(Action action, Consequence consequence){
        gameHistory.append(action, consequence);
    }

    /**
     * The npcIsWaitingForResponse() function iterates through all NPCs in the current room
     * and checks if any of them are waiting for a response from the player. 
     * 
     * @return (boolean) true if at least 1 npc is waiting for a response
     */
    public boolean npcIsWaitingForResponse(){
        ArrayList<Integer> npcs = getCurrentRoom().getNonPlayerCharacters();
        for(Integer npcIndex : npcs){
            if(story.getNonPlayerCharacter(npcIndex).isWaitingForResponse()){
                return true;
            }
        }
        return false;
    }

    /**
     * Utility function that calculates the Hero's total attack power, which 
     * is their base attack power plus the attack power of all equipped items. 
     * 
     * @return (int) the current attack power of the hero
     */
    public int getHeroAttackPower(){
        int apModifier = 0;
        ArrayList<Item> equippedItems = getEquippedItems();
        for(Item item : equippedItems){
            apModifier += item.getAttackPower();
        }
        return hero.getAttackPower() + apModifier;
    }

    /**
     * Utility function that calculates the total defence of the hero, which 
     * is the base defence of the hero plus the defence of any equipped items. 
     * 
     * @return (int) the current defence of the hero
     */
    public int getHeroDefence(){
        int defModifier = 0;
        ArrayList<Item> equippedItems = getEquippedItems();
        for(Item item : equippedItems){
            defModifier += item.getDefence();
        }
        return hero.getDefense() + defModifier;
    }
}
