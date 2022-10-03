package GameEngine;

import Story.Command;

import java.io.Serializable;

/**
 * The Action class represents an action that the player wishes to take.
 * It is created by the CommandParser, which interprets raw user input. 
 * It holds a Command and that command's target.
 * 
 * @author Samuel J. Brookes (u5380100)
 */
public class Action implements Serializable {
    
    private boolean commandIsLegal;
    private String illegalCommand;
    private Command command;
    private String target;

    /**
     * The constructor for an illegal Action. This is
     * necessary in case the user input does not generate
     * a valid Command/Target combination, but is still useable. 
     * For example, when the player is responding to a 
     * question from a NonPlayerCharacter.
     * 
     * @param illegalCommand (String)
     */
    public Action(String illegalCommand){
        commandIsLegal = false;
        this.illegalCommand = illegalCommand;
    }

    /**
     * The constructor for a legal Action. This holds a Command object and
     * the name of a target (String).
     * 
     * @param command (Story.Command)
     * @param target (String)
     */
    public Action(Command command, String target){
        commandIsLegal = true;
        this.command = command;
        this.target = target;
    }

    /**
     * Utility function to get the Command stored in the Action.
     * 
     * @return Story.Command command
     */
    public Command getCommand(){
        return command;
    }

    /**
     * Utility function to get the taarget stored in the Action.
     * 
     * @return String target
     */
    public String getTarget(){
        return target;
    }

    /**
     * Utility function that returns whether or not the
     * Action is valid (i.e. has a Command/target).
     * 
     * @return boolean commandIsLegal
     */
    public boolean isValid(){
        return commandIsLegal;
    }

    /**
     * Utility function that returns the illegalCommand
     * string of an illegal Action object.
     * 
     * @return String illegalCommand
     */
    public String getIllegalCommand(){
        return illegalCommand;
    }

    /**
     * Utility function that prints the Command/target
     * to a normalized String.
     * 
     * @return String illegalCommand or 'Command Target'
     */
    public String toString(){
        if(commandIsLegal){
            return String.format("%s %s", command.name(), target);   
        }else{
            return illegalCommand.toUpperCase();
        }
    }
}
