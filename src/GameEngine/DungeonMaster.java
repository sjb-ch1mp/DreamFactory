package GameEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import GameEngine.Exceptions.NoSuchItemException;
import GameEngine.Exceptions.NoValidTargetException;
import GameEngine.Exceptions.CommandNotLegalInThisModeException;
import Story.Container;
import Story.Direction;
import Story.Enemy;
import Story.Item;
import Story.NonPlayerCharacter;
import Story.Passage;
import Story.Response;
import Story.Passage.PassageType;

/**
 * The DungeonMaster class implements all the logic for Commands and how they are 
 * resolved when the GameEngine is in EXPLORATION mode. 
 * 
 * The DungeonMaster takes an Action made by the player and uses the current GameState
 * to calculate what the Consequences of that action are, given the current state of the game. 
 * 
 * The DungeonMaster does not consider Actions taken in the context of combat (i.e. when the GameEngine is
 * in COMBAT mode). These are handled by the Arena class.
 * 
 * @author Samuel J. Brookes (u5380100)
 */
public class DungeonMaster implements Serializable {
    
    private GameState gameState;
    private Action action;
    private Consequence consequence;

    /**
     * The constructor for the DungeonMaster. This requires an Action taken by the 
     * player, the current GameState and the Consequence object that will be updated by
     * the results of the action. 
     * 
     * @param gameState (GameState) the current game state
     * @param action (Action) the Action taken by the player
     * @param consequence (Consequence) the Consequence to be updated
     */
    public DungeonMaster(GameState gameState, Action action, Consequence consequence){
        this.gameState = gameState;
        this.action = action;
        this.consequence = consequence;
    }

    /**
     * Utility function that returns the GameState object stored in the 
     * DungeonMaster. 
     * 
     * @return (GameState) the gamestate
     */
    public GameState getGameState(){
        return gameState;
    }

    /**
     * This is the primary function of the DungeonMaster class.
     * It implements the logic for all EXPLORATION mode commands, resolving them
     * in the context of the current game state. 
     * 
     * The mediate() function updates the Consequence object that
     * is passed to the DungeonMaster with the results of the action taken, and 
     * then returns it to the GameEngine.
     * 
     * @return (Consequence) the updated consequence
     */
    public Consequence mediate(){
        try{
            if(gameState.npcIsWaitingForResponse()){
                
                    respond();
                
            }else{    
                switch(action.getCommand()){
                    case HELP: help(); break;
                    case GREET: greet(); break;
                    case ATTACK: attack(); break;
                    case SEARCH: search(); break;
                    case USE: use(); break;
                    case INSPECT: inspect(); break;
                    case GO: go(); break;
                    case LOOK: look(); break;
                    case EXPLORE: explore(); break;
                    case INVENTORY: showInventory(); break;
                    case SAY: say(); break;
                    default: return consequence; /* This shouldn't happen... */
                }
                
            }
        }catch(NoValidTargetException e){
            consequence.addToConsequence(e.toString());
        }catch(NoSuchItemException e){
            consequence.addToConsequence(e.toString());
        }catch(CommandNotLegalInThisModeException e){
            consequence.addToConsequence(e.toString());
        }

        return consequence;
    }

    /**
     * The help() function is called if the player uses the HELP command.
     * 
     * The consequence of this action is for all valid commands to be 
     * shown to the player. 
     */
    private void help(){
        consequence.addToConsequence("The following commands can be used when NOT IN COMBAT:\n");
        consequence.addToConsequence(" - GREET <TARGET>\n");
        consequence.addToConsequence(" - ATTACK <TARGET>\n");
        consequence.addToConsequence(" - SEARCH <TARGET>\n");
        consequence.addToConsequence(" - USE <TARGET>\n");
        consequence.addToConsequence(" - INSPECT <TARGET>\n");
        consequence.addToConsequence(" - GO <NORTH/SOUTH/EAST/WEST>\n");
        consequence.addToConsequence(" - LOOK <NORTH/SOUTH/EAST/WEST>\n");
        consequence.addToConsequence(" - SAY <ANYTHING>\n");
        consequence.addToConsequence(" - EXPLORE\n");
        consequence.addToConsequence(" - INVENTORY\n\n");

        consequence.addToConsequence("The following actions can be taken when IN COMBAT:\n");
        consequence.addToConsequence(" - ATTACK\n");
        consequence.addToConsequence(" - BLOCK\n");
        consequence.addToConsequence(" - DODGE\n");
        consequence.addToConsequence(" - ESCAPE\n\n");

        consequence.addToConsequence("The following actions can be taken at ANY TIME:\n");
        consequence.addToConsequence(" - HELP");
    }

    /**
     * The repsond() function is called if any NonPlayerCharacter 
     * is waiting for a response from the player. A NonPlayerCharacter
     * will wait for a repsonse if it is GREETed by the player and it
     * has a question.
     * 
     * The respond() function will throw a NoValidTargetException if
     * there are no NonPlayerCharacters waiting for a response, however
     * this should not happen.
     * 
     * @throws NoValidTargetException
     */
    private void respond() throws NoValidTargetException{

        //Find the npc that is waiting for a response
        HashMap<Integer, NonPlayerCharacter> npcs = gameState.getStory().getNonPlayerCharacters();
        NonPlayerCharacter expectantNPC = null;
        for(Map.Entry<Integer, NonPlayerCharacter> npc : npcs.entrySet()){
            if(npc.getValue().isWaitingForResponse()){
                expectantNPC = npc.getValue();
                break;
            }
        }

        //See if the player's response is valid - if so provide the npc's response
        if(expectantNPC == null){
            throw new NoValidTargetException(action, false);
        }else{
            if(expectantNPC.getResponses().containsKey(action.toString())){
                Response response = expectantNPC.getResponses().get(action.toString());
                switch(response.getType()){
                    case ITEM:
                        //add the item to your inventory
                        Item item = gameState.getStory().getItem(response.getItemIndex());
                        HashMap<Integer, Boolean> inventory = gameState.getHero().getInventory();
                        inventory.put(item.getIndex(), item.isEquippable());
                        gameState.getHero().setInventory(inventory);
                        consequence.addToConsequence(response.getResponseText());

                        //add the response to the consequence
                        consequence.addToConsequence(String.format(
                            "You get %s and %s",
                            item.getName(),
                            item.isEquippable() ? "equip it." : "add it to your inventory."
                        ));

                        //resolve npc
                        expectantNPC.setResolved(true);
                        break;
                    case ALTER_HEALTH:
                        //alter the hero's health
                        gameState.getHero().setHealth(gameState.getHero().getHealth() + response.getHealth());

                        //resolve npc
                        expectantNPC.setResolved(true);

                        //add the response to the consequence
                        consequence.addToConsequence(String.format("%s You %s %d health.", response.getResponseText(), response.getHealth() > 0 ? "gain": "lose", Math.abs(response.getHealth())));
                        break;
                    case TEXT:
                        //add the response to the consequence
                        consequence.addToConsequence(response.getResponseText());
                }
            }else{
                //The response is not a valid one for the question
                consequence.addToConsequence(String.format("%s doesn't understand your response.", expectantNPC.getName()));
            }

            //make the npc stop waiting for a response, and save it's new state
            expectantNPC.setWaitingForResponse(false);
            gameState.getStory().getNonPlayerCharacters().put(expectantNPC.getCharacterIndex(), expectantNPC);
        }
    }

    /**
     * The greet() function is called if the player uses the GREET command. This command
     * allows players to interact with NonPlayerCharacters.
     * 
     * If there is a NonPlayerCharacter with the same name as the target
     * in the Action, this function will result in their greeting being
     * shown to the player. Otherwise, a NoValidTargetException will be thrown.
     * 
     * @throws NoValidTargetException
     */
    private void greet() throws NoValidTargetException{
        NonPlayerCharacter targetNpc = getTargetNPC();
        Enemy targetEnemy = getTargetEnemy();
        Container targetContainer = getTargetContainer();
        if(targetNpc == null){
            throw new NoValidTargetException(action, targetEnemy != null || targetContainer != null);
        }else{
            consequence.addToConsequence(targetNpc.isResolved() ? targetNpc.getResolvedGreeting() : targetNpc.getGreeting());
            if(targetNpc.isHasQuestion() && !targetNpc.isResolved()){
                targetNpc.setWaitingForResponse(true);
                gameState.getStory().getNonPlayerCharacters().put(targetNpc.getCharacterIndex(), targetNpc);
            }
        }
    }

    /**
     * The attack() function is called when a player uses the ATTACK command in EXPLORATION mode. This command
     * allows players to attack an Enemy.
     * 
     * If the combat version of this command is used, e.g. "ATTACK_ENEMY", then a CommandNotLegalInThisModeException
     * will be thrown.
     * 
     * If there is an Enemy in the room with the same name as the target of the Action, then the current game mode
     * will be switched to COMBAT and an Arena object will be created and passed to the GameState. Otherwise, a 
     * NoValidTargetException will be thrown.
     * 
     * @throws NoValidTargetException
     * @throws CommandNotLegalInThisModeException
     */
    private void attack() throws NoValidTargetException, CommandNotLegalInThisModeException{
        if(action.getTarget().equals("")){
            throw new CommandNotLegalInThisModeException(gameState.getCurrentMode(), action.getCommand());
        }

        Enemy targetEnemy = getTargetEnemy();
        NonPlayerCharacter targetNpc = getTargetNPC();
        Container targetContainer = getTargetContainer();
        if(targetEnemy == null){
            throw new NoValidTargetException(action, targetNpc != null || targetContainer != null);
        }else{
            consequence.addToConsequence(String.format("You choose to attack %s. Begin combat!", targetEnemy.getName()));

            gameState.setArena(new Arena(gameState.getHero(), gameState.getEquippedItems(), targetEnemy));
            gameState.toggleMode();
            
        }
    }

    /**
     * The search() function will be called when the player uses the SEARCH command. This command
     * allows players to loot containers that are unlocked. 
     * 
     * If there is a container in the room with the same name as the Action's target, then any items 
     * in the container will be added to the inventory of the Hero. Otherwise a NoValidTargetException
     * will be thrown. 
     * 
     * @throws NoValidTargetException
     */
    private void search() throws NoValidTargetException{
        Container targetContainer = getTargetContainer();
        NonPlayerCharacter targetNpc = getTargetNPC();
        Enemy targetEnemy = getTargetEnemy();
        if(targetContainer == null){
            throw new NoValidTargetException(action, targetNpc != null || targetEnemy != null);
        }else{
            if(targetContainer.isLocked()){
                consequence.addToConsequence(String.format("%s is locked.", targetContainer.getName()));
            }else if(targetContainer.isLooted()){
                consequence.addToConsequence(String.format("There is nothing of interest in the %s", targetContainer.getName()));
            }else{

                //Get the loot from the container
                Item loot = gameState.getStory().getItem(targetContainer.getItemIndex());
                gameState.getHero().getInventory().put(loot.getIndex(), loot.isEquippable());
                consequence.addToConsequence(
                    String.format(
                        "You find a %s in the %s.%s",
                        loot.getName(), 
                        targetContainer.getName(),
                        loot.isEquippable() ? " You equip it." : ""
                    )
                );
                targetContainer.setLooted(true);
                gameState.getStory().getContainers().put(targetContainer.getContainerIndex(), targetContainer);
            }
        }
    }

    /**
     * The use() function is called when a player uses the USE command. This command
     * allows players to interact with items in their inventory. 
     * 
     * If there is an item in the Hero's inventory with a name that matches the Action's 
     * target, the player will successful interact with it. Otherwise a NoSuchItemException
     * will be thrown.
     * 
     * If the item is consumable, the Hero's stats will be updated according to that 
     * item's health, attack power and defence. 
     * 
     * If the item is a key for a Container or Passage in the room, that Container or Passage
     * will be unlocked (if it wasn't already), otherwise a NoValidTargetException will be thrown.
     * 
     * @throws NoValidTargetException
     * @throws NoSuchItemException
     */
    private void use() throws NoValidTargetException, NoSuchItemException{
        //check inventory isConsumable
        Item targetItem = getTargetInventoryItem();
        if(targetItem != null && targetItem.isConsumable() && !targetItem.isEmpty()){
            consequence.addToConsequence(String.format("You use %s.", targetItem.getName()));
            if(Math.abs((double) targetItem.getHealth()) > 0){
                gameState.getHero().setHealth(gameState.getHero().getHealth() + targetItem.getHealth());
                consequence.addToConsequence(String.format(" You %s %d health!", targetItem.getHealth() > 0 ? "gain" : "lose", (int) Math.abs((double) targetItem.getHealth())));
            }
            if(Math.abs((double) targetItem.getAttackPower()) > 0){
                gameState.getHero().setAttackPower(gameState.getHero().getAttackPower() + targetItem.getAttackPower());
                consequence.addToConsequence(String.format(" You %s %d attack power!", targetItem.getAttackPower() > 0 ? "gain" : "lose", (int) Math.abs((double) targetItem.getAttackPower())));
            }
            if(Math.abs((double) targetItem.getDefence()) > 0){
                gameState.getHero().setDefense(gameState.getHero().getDefense() + targetItem.getDefence());
                consequence.addToConsequence(String.format(" You %s %d defence!", targetItem.getDefence() > 0 ? "gain" : "lose", (int) Math.abs((double) targetItem.getDefence())));
            }
            targetItem.useConsumableItem();
            gameState.getStory().getItems().put(targetItem.getIndex(), targetItem);
            gameState.getHero().getInventory().remove(targetItem.getIndex());
            return;
        }else if(targetItem == null){
            throw new NoSuchItemException(action);
        }


        //check passages
        Passage targetPassage = getTargetPassageByKey();
        if(targetPassage != null){
            if(targetPassage.isLocked()){
                consequence.addToConsequence(targetPassage.getDescriptionAction());
                targetPassage.setLocked(false);
                gameState.getStory().getPassages().put(targetPassage.getPassageIndex(), targetPassage);
            }else{
                consequence.addToConsequence(String.format("You use %s, but you can already go %s.", targetItem.getName(), getPassageDirectionByIndex(targetPassage.getPassageIndex())));
            }
            return;
        }

        //check containers
        Container targetContainer = getTargetContainerByKey();
        if(targetContainer != null){
            if(targetContainer.isLocked()){
                consequence.addToConsequence(targetContainer.getDescriptionAction());
                targetContainer.setLocked(false);
                gameState.getStory().getContainers().put(targetContainer.getContainerIndex(), targetContainer);
            }else{
                consequence.addToConsequence(String.format("You use %s on %s, but it is already unlocked!", targetItem.getName(), targetContainer.getName()));
            }
            return;
        }

        throw new NoValidTargetException(action, false);
    }

    /**
     * The inspect() function is called if the player uses the INSPECT command. This 
     * command allows a player to get the description for items in the Hero's inventory, or 
     * enemies, containers, or non-player characters in the room. 
     * 
     * If the target of the Action matches the name of an item, enemy, container or npc in the room, 
     * the description of that target will be shown to the player. Otherwise a NoValid TargetException 
     * will be thrown. 
     * 
     * @throws NoValidTargetException
     */
    private void inspect() throws NoValidTargetException{
        //inspect items in inventory
        Item targetItem = getTargetInventoryItem();
        if(targetItem != null){
            consequence.addToConsequence(targetItem.getDescription());
            return;
        }

        //inspect containers in room
        Container targetContainer = getTargetContainer();
        if(targetContainer != null){
            consequence.addToConsequence(targetContainer.isLocked() ? targetContainer.getDescriptionLocked(): targetContainer.getDescriptionUnlocked());
            consequence.addToConsequence(targetContainer.isLooted() ? "It is empty." : "You see something inside.");
            return;
        }

        //inspect enemies in room
        Enemy targetEnemy = getTargetEnemy();
        if(targetEnemy != null){
            consequence.addToConsequence(targetEnemy.getDescription());
            return;
        }

        //inspect npcs in room
        NonPlayerCharacter targetNpc = getTargetNPC();
        if(targetNpc != null){
            consequence.addToConsequence(targetNpc.getDescription());
            return;
        }

        throw new NoValidTargetException(action, false);
    }

    /**
     * The go() function is called when the player uses the GO command. This
     * command allows the player to move from the current room to another room via
     * a passage. 
     * 
     * If the target of the Action is a valid Direction, and the passage at that
     * direction is not PASSAGE_NONE and is unlocked, then the current room will be updated
     * to the room that is linked to that passage and the player will be shown the new room's description. 
     * Otherwise a NoValidTargetException will be thrown. 
     * 
     * @throws NoValidTargetException
     */
    private void go() throws NoValidTargetException{
        for(Direction direction : Direction.values()){
            if(action.getTarget().equals(direction.name())){
                Passage passage = gameState.getStory().getPassage(gameState.getCurrentRoom().getPassages().get(direction));
                if(passage.getPassageType() == PassageType.PASSAGE_NONE || passage.isLocked()){
                    consequence.addToConsequence(String.format("You cannot go %s.", direction.name()));
                }else{
                    consequence.addToConsequence(String.format("You go %s.\n\n", direction.name()));
                    gameState.moveToNewRoom(passage);
                    consequence.addToConsequence(String.format("[%s]\n\n", gameState.getCurrentRoom().getRoomName()));
                    consequence.addToConsequence(gameState.getCurrentRoom().getDescription());
                    if(gameState.getCurrentRoom().isLastRoom()){
                        gameState.gameOver();
                    }
                }
                return;
            }
        }

        throw new NoValidTargetException(action, false);
    }

    /**
     * The look() function is called when the player uses the LOOK command. This
     * command allows players to get the description of a passage without moving through
     * that passage. 
     * 
     * If the target of the Action is a valid direction, the description of the passage
     * in that direction will be shown to the player, otherwise a NoValidTargetException 
     * will be thrown. 
     * 
     * @throws NoValidTargetException
     */
    private void look() throws NoValidTargetException{
        for(Direction direction : Direction.values()){
            if(action.getTarget().equals(direction.name())){
                Passage passage = gameState.getStory().getPassage(gameState.getCurrentRoom().getPassages().get(direction));
                consequence.addToConsequence(passage.isLocked() ? passage.getDescriptionLocked(): passage.getDescriptionUnlocked());
                return;
            }
        }

        throw new NoValidTargetException(action, false);
    }

    /**
     * The explore() function is called when the player uses the EXPLORE function. 
     * This command allows players to list all interactable features within the current room. 
     * 
     * This command will show the description of all containers, enemies, non-player characters, and
     * passages in the current room to the player. 
     */
    private void explore(){
        ArrayList<Integer> npcs = gameState.getCurrentRoom().getNonPlayerCharacters();
        ArrayList<Integer> enemies = gameState.getCurrentRoom().getEnemies();
        ArrayList<Integer> containers = gameState.getCurrentRoom().getContainers();
        HashMap<Direction, Integer> passages = gameState.getCurrentRoom().getPassages();
        
        String roomFeatures = "";
        if(npcs != null && npcs.size() > 0){
            for(Integer npcIndex : npcs){
                roomFeatures += String.format("%s", gameState.getStory().getNonPlayerCharacter(npcIndex).getDescription());
            }
        }
        if(enemies != null && enemies.size() > 0){
            for(Integer enemyIndex : enemies){
                roomFeatures += String.format(" %s", gameState.getStory().getEnemy(enemyIndex).getDescription());
            }
        }
        for(Map.Entry<Direction, Integer> entry : passages.entrySet()){
            Passage passage = gameState.getStory().getPassage(entry.getValue());
            roomFeatures += String.format(" You look %s. %s", entry.getKey().name(), passage.isLocked() ? passage.getDescriptionLocked() : passage.getDescriptionUnlocked());
        }
        if(containers != null && containers.size() > 0){
            for(Integer containerIndex : containers){
                Container container = gameState.getStory().getContainer(containerIndex);
                roomFeatures += String.format(" %s", container.isLocked() ? container.getDescriptionLocked(): container.getDescriptionUnlocked());
            }
        }

        if(roomFeatures.length() == 0){
            consequence.addToConsequence(String.format("You look around the %s, but you don't see anything of interest.", gameState.getCurrentRoom().getRoomName()));
        }else{
            consequence.addToConsequence(String.format("You look around the %s. %s", gameState.getCurrentRoom().getRoomName(), roomFeatures.trim()));
        }
    }

    /**
     * The showInventory() function is called when a player uses the INVENTORY command.
     * This command allows the player to view the description of all items in their inventory. 
     * 
     * If the item is equipped, this will be indicated with "[EQUIPPED]" and the affects of 
     * this item on the players stats will be shown. 
     * 
     * If the item is consumable, this will be indicated with "[CONSUMABLE]" and whether the item
     * is full or empty will be shown.
     */
    private void showInventory(){
        if(gameState.getHero().getInventory().size() > 0){
            consequence.addToConsequence("You have the following items in your inventory:\n");
            
            HashMap<Integer, Boolean> inventory = gameState.getHero().getInventory();
            for(Map.Entry<Integer, Boolean> entry : inventory.entrySet()){
                Item item = gameState.getStory().getItem(entry.getKey());
                String itemDescription = String.format(" - %s", item.getName());
                if(entry.getValue()){ //item is equipped
                    itemDescription += " [EQUIPPED]";
                    if(item.getAttackPower() > 0){
                        itemDescription += String.format(" [+%d AP]", item.getAttackPower());
                    }
                    if(item.getDefence() > 0){
                        itemDescription += String.format(" [+%d DEF]", item.getDefence());
                    }
                }else if(item.isConsumable()){
                    itemDescription += String.format(" [CONSUMABLE] [%s]", item.isEmpty() ? "EMPTY" : "FULL");
                }
                consequence.addToConsequence(String.format("%s\n", itemDescription));
            }
        }else{
            consequence.addToConsequence("There is nothing in your inventory.");
        }
    }

    /**
     * The say() function is called when the player uses the SAY command. This
     * command allows the player to pass any string to the game engine. The SAY 
     * command is similar to the USE command in that it can be used as a key for 
     * a passage or container. 
     * 
     * If the target of the Action matches the key string of any container or passage
     * in the room, that container or passage will be unlocked, if it isn't already. Otherwise
     * a NoValidTargetException will be thrown. 
     * 
     * @throws NoValidTargetException
     */
    private void say() throws NoValidTargetException{
        //check passages
        Passage targetPassage = getTargetPassageByKey();
        if(targetPassage != null && targetPassage.isLocked()){
            gameState.getStory().getPassage(targetPassage.getPassageIndex()).setLocked(false);
            consequence.addToConsequence(targetPassage.getDescriptionAction());
            return;
        }

        //check containers
        Container targetContainer = getTargetContainerByKey();
        if(targetContainer != null && targetContainer.isLocked()){
            gameState.getStory().getContainer(targetContainer.getContainerIndex()).setLocked(false);
            consequence.addToConsequence(targetContainer.getDescriptionAction());
            return;
        }

        throw new NoValidTargetException(action, false);
    }

    /**
     * Utility function that retrieves a container from the Story based on whether
     * it's name matches the target of the Action. 
     * 
     * @return (Container/null) the target container, or null if there is no matching container name
     */
    private Container getTargetContainer(){
        ArrayList<Integer> containers = gameState.getCurrentRoom().getContainers();
        for(Integer containerIndex : containers){
            if(gameState.getStory().getContainer(containerIndex).getName().equals(action.getTarget())){
                return gameState.getStory().getContainer(containerIndex);
            }
        }
        return null;
    }

    /**
     * Utility function that retrieves an enemy from the Story based on whether it's name matches
     * the target of the Action. 
     * 
     * @return (Enemy/null) the target enemy, or null if there is no matching enemy name
     */
    private Enemy getTargetEnemy(){
        ArrayList<Integer> enemies = gameState.getCurrentRoom().getEnemies();
        for(Integer enemyIndex : enemies){
            if(gameState.getStory().getEnemy(enemyIndex).getName().equals(action.getTarget())){
                return gameState.getStory().getEnemy(enemyIndex);
            }
        }
        return null;
    }

    /**
     * Utility function that retrives an item from the Hero's inventory based on whether it's 
     * name matches the target of the Action.
     * 
     * @return (Item/null) the target item, or null if there is no matching item name
     */
    private Item getTargetInventoryItem(){
        HashMap<Integer, Boolean> inventory = gameState.getHero().getInventory();
        for(Map.Entry<Integer, Boolean> entry : inventory.entrySet()){
            if(gameState.getStory().getItem(entry.getKey()).getName().equals(action.getTarget())){
                return gameState.getStory().getItem(entry.getKey());
            }
        }
        return null;
    }

    /**
     * Utility function that retrieves a NonPlayerCharacter from the Story based on whether
     * it's name matches the target of the Action. 
     * 
     * @return (NonPlayerCharacter/null) the target npc, or null if there is no matching npc name
     */
    private NonPlayerCharacter getTargetNPC(){
        ArrayList<Integer> npcs = gameState.getCurrentRoom().getNonPlayerCharacters();
        for(Integer npcIndex : npcs){
            if(gameState.getStory().getNonPlayerCharacter(npcIndex).getName().equals(action.getTarget())){
                return gameState.getStory().getNonPlayerCharacter(npcIndex);
            }
        }
        return null;
    }

    /**
     * Utility function that retrieves a Passage from the current Room based on whether
     * it's key string matches the Action.
     * 
     * @return (Passage) the target passage, or null if there is no matching passage key
     */
    private Passage getTargetPassageByKey(){
        HashMap<Direction, Integer> passages = gameState.getCurrentRoom().getPassages();
        for(Map.Entry<Direction, Integer> entry : passages.entrySet()){
            if(gameState.getStory().getPassage(entry.getValue()).getKey().equals(action.toString())){
                return gameState.getStory().getPassage(entry.getValue());
            }
        }
        return null;
    }

    /**
     * Utility function that retrieves a Container from the current room based on whether 
     * it's key string matches the Action. 
     * 
     * @return (Container) the target Container, or null if there is no matching container key
     */
    private Container getTargetContainerByKey(){
        ArrayList<Integer> containers = gameState.getCurrentRoom().getContainers();
        for(Integer containerIndex : containers){
            if(gameState.getStory().getContainer(containerIndex).getKey().equals(action.toString())){
                return gameState.getStory().getContainer(containerIndex);
            }
        }
        return null;
    }

    /**
     * Utility function that retrieves the direction of a specific passage in the room.
     * 
     * @param passageIndex (int) the index of the passage for which the direction is required
     * @return (Direction) the direction of the passage
     */
    private Direction getPassageDirectionByIndex(int passageIndex){
        for(Map.Entry<Direction, Integer> entry : gameState.getCurrentRoom().getPassages().entrySet()){
            if(entry.getValue() == passageIndex){
                return entry.getKey();
            }
        }
        return null;
    }
}