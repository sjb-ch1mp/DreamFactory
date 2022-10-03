package GameEngine;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The GameHistory class stores all Actions that have have been taken
 * by the player, and their corresponding Consequences, throughout the course of the game.
 * It provides utility functions so that the UI can iterate through the contents
 * of the list. 
 * 
 * @author Samuel J. Brookes (u5380100)
 */
public class GameHistory implements Serializable {
    
    private ArrayList<ActionConsequence> actionsAndConsequences;

    /**
     * The constructor of the GameHistory class initializes
     * an array list of ActionConsequence objects
     */
    public GameHistory(){
        this.actionsAndConsequences = new ArrayList<ActionConsequence>();
    }

    /**
     * The append() function adds a new Action-Consequence pair to the 
     * gamehistory, stored in an ActionConsequence object. 
     * 
     * @param action (Action) The action to store
     * @param consequence (Consequence) The corresponding consequence for that action
     */
    public void append(Action action, Consequence consequence){
        actionsAndConsequences.add(new ActionConsequence(action, consequence));
    }

    /**
     * Utility function for iterating through a GameHistory.
     * 
     * @return (int) the size of the array list in the GameHistory object
     */
    public int size(){
        return actionsAndConsequences.size();
    }

    /**
     * Utiltiy function for iterating through a GameHistory.
     * 
     * @param index (int) the current index in the loop
     * @return (Action) the action at that index
     */
    public Action getAction(int index){
        return actionsAndConsequences.get(index).action;
    }

    /**
     * Utility function for iterating through a GameHistory.
     * 
     * @param index (int) the current index in the loop
     * @return (Consequence) the consequence at that index
     */
    public Consequence getConsequence(int index){
        return actionsAndConsequences.get(index).consequence;
    }

    /**
     * The ActionConsequenc class is a private utility class
     * that allows an Action and it's corresponding Consequence
     * to be stored as a single element in an ArrayList.
     * 
     * It serves no purpose beyond that.
     */
    private class ActionConsequence implements Serializable{

        Action action;
        Consequence consequence;

        /**
         * The constructor for an ActionConsequence requires
         * an Action, and it's corresponding Consequence.
         * @param action
         * @param consequence
         */
        public ActionConsequence(Action action, Consequence consequence){
            this.action = action;
            this.consequence = consequence;
        }
    }
}
