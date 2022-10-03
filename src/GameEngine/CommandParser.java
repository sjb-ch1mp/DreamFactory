package GameEngine;

import java.io.Serializable;
import java.util.regex.Pattern;

import GameEngine.Exceptions.CommandNotLegalInThisModeException;
import GameEngine.Exceptions.NoSuchCommandException;
import Story.Command;
import Story.Mode;

/**
 * The CommandParser takes raw user input and transforms it into an Action 
 * class with a Command and a target. This is useful because the Action class
 * allows user input to be rendered in a normalized format. 
 * 
 * The CommandParser will throw a NoSuchCommandException if the user input resolves
 * to a Command that does not exist.
 * 
 * The CommandParser will throw a CommandNotLegalInThisModeException if the user input
 * resolves to a Command that cannot be used in the current Mode.
 * 
 * @author Samuel J. Brookes (u5380100)
 */
public class CommandParser implements Serializable {
    
    public CommandParser(){}

    /**
     * The CommandParser contains only one function, and this is it. The parse() function
     * will take the raw user input and transform it into an Action object. 
     * 
     * @param userInput (String) raw user input
     * @param currentMode (Mode) the current Mode, according to the GameEngine
     * @return (Action) the action to which the user input resolves to
     * @throws NoSuchCommandException 
     * @throws CommandNotLegalInThisModeException
     */
    public Action parse(String userInput, Mode currentMode) throws NoSuchCommandException, CommandNotLegalInThisModeException{

        //Clean up user input by making upper case and removing multiple spaces and not words/digits
        userInput = userInput.toUpperCase().trim();
        Pattern multipleSpaces = Pattern.compile("\\s+");
        Pattern notWords = Pattern.compile("\\c+");
        userInput = multipleSpaces.matcher(userInput).replaceAll(" ");
        userInput = notWords.matcher(userInput).replaceAll("");

        //Check if command exists
        String command = userInput.split("\\s+")[0];
        String target = userInput.substring(userInput.indexOf(" ") + 1);
        Action action = new Action("INVALID");

        //Check if this is ATTACK_ENEMY, or a EXPLORATION command without a target
        if(command.equals("HELP")){
            action = new Action(Command.HELP, "");
        }else if(currentMode == Mode.COMBAT && command.equals("ATTACK")){
            action = new Action(Command.ATTACK_ENEMY, "");
        }else if(currentMode == Mode.EXPLORATION && command.equals("EXPLORE")){
            action = new Action(Command.EXPLORE, "");
        }else if(currentMode == Mode.EXPLORATION && command.equals("INVENTORY")){
            action = new Action(Command.INVENTORY, "");
        }else if(currentMode == Mode.EXPLORATION && command.equals("ATTACK") && target.equals("ATTACK")){
            action = new Action(Command.ATTACK, "");
        }else{ //Process all other commands in the same way
            for(Command c : Command.values()){
                if(c.name().equals(command)){
                    action = new Action(c, ((currentMode == Mode.COMBAT) ? "" : target));
                    break;
                }
            }
        }
        
        //If a valid action has not yet been created, then this command does not exist
        if(!action.isValid()){
            throw new NoSuchCommandException(String.format("Command '%s' does not exist", command));
        }else{
            //If this is a valid action - check that it's being used in the correct mode
            if(action.getCommand().getValidMode() != Mode.ANY){
                if(currentMode == Mode.COMBAT){
                    if(action.getCommand().getValidMode() != Mode.COMBAT){
                        throw new CommandNotLegalInThisModeException(currentMode, action.getCommand());
                    }
                }else{
                    if(action.getCommand().getValidMode() != Mode.EXPLORATION){
                        throw new CommandNotLegalInThisModeException(currentMode, action.getCommand());
                    }
                }
                return action;
            }else{
                return action;
            }
        }
    }
}
