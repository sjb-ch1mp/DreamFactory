package GameEngine;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Stack;

import GameEngine.Exceptions.*;
import Story.Command;
import Story.Enemy;
import Story.Hero;
import Story.Item;
import Story.Mode;
import Story.Story;
import SaveLoad.SaveNLoad;

/**
 * The GameEngine class is the logic engine for the game. It interprets user input with a
 * CommandParser object, tracks the game's state with a GameState object, implements combat 
 * with Arena objects, resolves EXPLORATION mode actions with DungeonMaster objects and 
 * stores the history of the game in a GameHistory object. 
 * 
 * @author Samuel J. Brookes (u5380100)
 */
public class GameEngine implements Serializable{
    
    private GameState gameState;
    private int autoSaveCount = 0;
    private SaveNLoad saveNLoad=new SaveNLoad();

    /**
     * The constructor for the GameEngine. In order to initialize a GameState
     * object, the constructor requires a Story object. 
     * @param story
     */
    public GameEngine(Story story){
        Hero hero = new Hero(
            story.getInitialHeroHealth(), 
            story.getInitialHeroAttackPower(), 
            story.getInitialHeroDefence(),
            new HashMap<Integer, Boolean>()
        );
        this.gameState = new GameState(
            hero,
            story
        );
    }

    /**
     * The startStory function returns the first Consequence object for the story. 
     * This consists of the introduction text for the story, as well as the description 
     * of the first room. 
     * 
     * @return (Consequence) The introduction text for the story
     */
    public Consequence startStory(){
        Consequence consequence = new Consequence();
        consequence.addToConsequence(
            String.format(
                "%s\n\n[%s]\n\n%s\n\n(Type 'HELP' to view available commands)", 
                gameState.getStory().getIntroduction().trim(), 
                gameState.getCurrentRoom().getRoomName(), 
                gameState.getCurrentRoom().getDescription()
            )
        );
        return finalizeConsequence(new Action("BEGIN"), consequence);
    }

    /**
     * The sendCommand() method is the primary command for the GameEngine class. 
     * This function receives input from the user, interprets it in the context of
     * the current game state, resolves the action and returns a Consequence object
     * to be rendered by the user interface.
     * 
     * @param (userInput) userInput - raw input from the user
     * @return (Consequence) The consequence of the action taken by the player
     */

    public Consequence sendCommand(String userInput) {

        Consequence consequence = new Consequence();
        Action action = new Action(userInput);

        // Autosave Feature after every 10 commands.
        autoSaveCount++;
        if (autoSaveCount == 10) {
            autoSaveCount = 0;
            autosave();
        }


        //Check if hero is dead or game is finished
        if(gameState.getCurrentMode() == Mode.GAMEOVER){
            if(gameState.getHero().getHealth() <= 0){
                consequence.addToConsequence("[GAME OVER] You are dead. Better luck next time!");    
            }else if(gameState.getCurrentRoom().isLastRoom()){
                consequence.addToConsequence("[GAME OVER] You finished the story! Thanks for playing!");
            }
            return finalizeConsequence(action, consequence);
        }

        //Check if a nonPlayerCharacter is waiting for a response - because this can be any text at all
        if(gameState.npcIsWaitingForResponse()){
            DungeonMaster dungeonMaster = new DungeonMaster(gameState, action, consequence);
            consequence = dungeonMaster.mediate();
            gameState = dungeonMaster.getGameState();
            return finalizeConsequence(action, consequence);
        }

        //Check if command is legal and being used in the correct Mode
        try{
            action = new CommandParser().parse(userInput, gameState.getCurrentMode());
            if(action.getCommand() == Command.HELP){
                return finalizeConsequence(action, new DungeonMaster(gameState, action, consequence).mediate());
            }
        }catch(NoSuchCommandException e){
            if(gameState.getCurrentMode() != Mode.COMBAT){ //player 'fumbles' if in combat
                consequence.addToConsequence("You look confused.");    
                return finalizeConsequence(action, consequence);
            }            
        }catch(CommandNotLegalInThisModeException e){
            if(gameState.getCurrentMode() != Mode.COMBAT){ //player 'fumbles' if in combat
                consequence.addToConsequence(e.toString());
                return finalizeConsequence(action, consequence);
            }
        }

        //If hero is not dead and command is valid - the game continues - check if they're currently in combat
        if(gameState.getCurrentMode() == Mode.COMBAT){

            //Do next round of fighting
            Arena arena = gameState.getArena();
            consequence = arena.fight(action, consequence);

            //Check if enemy is dead, hero is dead or hero has escaped
            if(arena.enemyIsDead()){ //If enemy is dead - combat has ended

                //Save enemy and hero to gameState
                finalizeCombat(arena);
                gameState.toggleMode();
                consequence.addToConsequence(String.format("You have killed %s!", arena.getEnemy().getName()));

                //Check for loot on the dead enemy
                if(arena.getEnemy().dropsLoot()){

                    //Update the hero's inventory
                    Item loot = gameState.getStory().getItem(arena.getEnemy().getItemIndex());
                    Hero hero = gameState.getHero();
                    HashMap<Integer, Boolean> inventory = hero.getInventory();
                    inventory.put(loot.getIndex(), loot.isEquippable());
                    hero.setInventory(inventory);
                    gameState.updateHero(hero);
                    
                    //Report the loot drop to the player
                    consequence.addToConsequence(String.format("%s drops item '%s'.%s", arena.getEnemy().getName(), loot.getName(), loot.isEquippable() ? " You equip it": ""));
                }

            }else if(arena.heroHasEscaped()){ //If hero has escaped - combat has ended
                
                //Save enemy and hero to gameState
                finalizeCombat(arena);
                gameState.toggleMode();

            }else if(arena.heroIsDead()){ //If hero is dead - game has ended

                //End game
                gameState.gameOver();
                consequence.addToConsequence("[GAME OVER] You are dead. Better luck next time!");

            }else{ //If none of the previous conditions are true - combat continues
                
                //Save the arena
                gameState.setArena(arena);
            }

        }else{ //Otherwise, they are currently exploring
            
            DungeonMaster dungeonMaster = new DungeonMaster(gameState, action, consequence);
            consequence = dungeonMaster.mediate();

            if(gameState.getHero().getHealth() <= 0){ //If hero is dead - game has ended

                //End game
                gameState.gameOver();
                consequence.addToConsequence("[GAME OVER] You are dead. Better luck next time!");

            }

            gameState = dungeonMaster.getGameState();

        }

        return finalizeConsequence(action, consequence);
    }

    /**
     * The loadGame function will take a path to a save file which 
     * and call the function GameSaver.loadGame(), which will return
     * a GameState object containing a GameHistory. The GameHistory
     * will then be returned to the UI component to be rendered, while
     * the GameState from the save file will replace the current GameState
     * in the GameEngine.
     * 
     * @return (GameHistory) The game history of the saved game. 
     */
    public GameHistory loadGame() throws NoSavedGameException {
        GameState gameRecord;
        gameRecord = saveNLoad.loadGame();
        if(gameRecord != null){
            return gameRecord.getGameHistory();
        }
        throw new NoSavedGameException();
    }

    /**
     * The saveGame function will pass the current gameState
     * to a gameSaver object to be written to file. 
     */
    public void saveGame(){
        System.out.println("save game in gameengine");
        saveNLoad.saveGame(getGameState());
    }

    /**
     * This getter will return the current gamestate held by the GameEngine.
     * This is used by the GameSaver to save the game as a file. 
     * 
     * @return (GameState) The current gamestate held by the GameEngine
     */
    public GameState getGameState(){
        return gameState;
    }

    /**
     * The finalizeConsequence() function is a utility function that 
     * updates the Consequence object with the Action that was taken
     * as well as the current hero stats.
     * 
     * @param (action) action - the action that was taken this turn
     * @param (consequence) consequence - the consequence before being updated
     * @return (Consequence) - the updated consequence for this turn
     */
    private Consequence finalizeConsequence(Action action, Consequence consequence){

        //Prepare consequence with all the details necessary for the UI
        consequence.setAction(action);
        consequence.updateHeroHealth(gameState.getHero().getHealth());
        consequence.updateHeroAttackPower(gameState.getHeroAttackPower());
        consequence.updateHeroDefence(gameState.getHeroDefence());

        //Save the action/consequence to the game history
        gameState.getGameHistory().append(action, consequence);

        return consequence;
    }

    /**
     * The finalizeCombat() function is a utility function that
     * saves the state of the enemy and hero.
     * 
     * @param (arena) arena - the arena that was used to host the combat
     */
    private void finalizeCombat(Arena arena){
        //Save the enemy
        HashMap<Integer, Enemy> enemies = gameState.getStory().getEnemies();
        enemies.put(arena.getEnemy().getEnemyIndex(), arena.getEnemy());
        gameState.getStory().setEnemies(enemies);

        //Save the hero
        gameState.updateHero(arena.getHero());
    }

    /**
     * @author Shafin Kamal
     *
     * the autosave() function will observe the Stack and check if it was called by the automatic game tester.
     *
     * If it was called by the AGT, do NOT autosave as it will overwrite user progress. Else, autosave.
     */
    public void autosave() {

        // Get stack frames
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        for (int i = 1; i < stackTraceElements.length && i <= 5; i++) {
            StackTraceElement stackTraceElement = stackTraceElements[i];

            // if the Automatic Game Tester is called, it is at stack index 3 - thus check at index 3.
            if (i == 3) {
                if (stackTraceElement.getClassName().equals("test.AutomaticTester.AutomaticGameTester")) {
                    System.out.println("Automatic Game Tester (AGT) detected. Autosaving function is " +
                            "DISABLED.");
                    break;

                    // Automatic Game Tester not detected. Save game.
                } else {
                    saveGame();
                    System.out.println("Game as been autosaved.");
                    break;
                }
            }
        }
    }

}
