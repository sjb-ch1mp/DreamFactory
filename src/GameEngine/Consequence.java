package GameEngine;

import Story.Command;

import java.io.Serializable;

/**
 * The Consequence class contains all the information necessary
 * for the results of a given action taken by the player to be
 * rendered in the UI.
 *
 * A Consequence object is instantiated and then passed between
 * functions and updated with the results of those functions.
 *
 * @author Samuel J. Brookes (u5380100)
 */
public class Consequence implements Serializable {
    
    private Action action;
    private StringBuilder consequenceDescription;
    private int currentHeroHealth;
    private int currentHeroDefence;
    private int currentHeroAttackPower;

    /**
     * The constructor for Consequence instantiates the consequenceDescription variable.
     */
    public Consequence(){
        this.consequenceDescription = new StringBuilder(); 
    }

    /**
     * Utility function to change the Action stored in a Consequence.
     *
     * @param action (Action) the new Action object
     */
    public void setAction(Action action){
        this.action = action;
    }

    /**
     * Utility function that prints the contents of the Action object
     * in a normalized format.
     *
     * @return (String) The contents of the stored Action (Command Target)
     */
    public String getActionText(){
        return action.getCommand() == Command.ATTACK_ENEMY ? "ATTACK" : action.toString();
    }

    public boolean getActionValid(){
        return action.isValid();
    }


    /**
     * The addToConsequence() function allows functions to update the
     * consequence desription with their results.
     *
     * @param newConsequence (String) a new consequence to append to the consequence description
     */
    public void addToConsequence(String newConsequence){
        consequenceDescription.append(String.format(" %s", newConsequence));
    }

    /**
     * Utility function that returns the consequence description.
     *
     * @return (String) the complete consequence description
     */
    public String getConsequenceDescription(){
        return consequenceDescription.toString();
    }

    /**
     * Utility function to update the Hero's health value stored in the
     * consequence.
     *
     * @param newHealth (int) the new health
     */
    public void updateHeroHealth(int newHealth){
        currentHeroHealth = newHealth;
    }

    /**
     * Utility function to update the Hero's attack power stored in the
     * consequence.
     *
     * @param newAttackPower (int) the new attack power
     */
    public void updateHeroAttackPower(int newAttackPower){
        currentHeroAttackPower = newAttackPower;
    }

    /**
     * Utility function to update the Hero's defence stored in the consequence.
     *
     * @param newDefence (int) the new defence
     */
    public void updateHeroDefence(int newDefence){
        currentHeroDefence = newDefence;
    }

    /**
     * Utility function that returns the health value stored
     * in the consequence. This should be the hero's health after
     * the action taken by the player is resolved.
     *
     * @return (int) the hero's health
     */
    public int getCurrentHeroHealth(){
        return currentHeroHealth;
    }

    /**
     * Utility function that returns the attack power value stored
     * in the consequence. This should be the hero's attack power after
     * the action taken by the player is resolved.
     *
     * @return (int) the hero's attack power
     */
    public int getCurrentHeroDefence(){
        return currentHeroDefence;
    }

    /**
     * Utility function that returns the defence value stored
     * in this consequence. This should be the hero's defence after
     * the action taken by the player is resolved.
     *
     * @return (int) the hero's defence
     */
    public int getCurrentHeroAttackPower(){
        return currentHeroAttackPower;
    }
}
