package GameEngine.Exceptions;

import Story.Command;
import Story.Mode;

public class CommandNotLegalInThisModeException extends Exception{
    
    private Mode currentMode;
    private Command command;

    public CommandNotLegalInThisModeException(Mode currentMode, Command command){
        this.currentMode = currentMode;
        this.command = command;
    }

    public String toString(){
        if(currentMode == Mode.COMBAT){ //Player is currently in combat
            switch(command){
                case ATTACK:
                    return "You are already in combat!";
                case INVENTORY:
                    return "You are being attacked! You don't have time to look at your inventory!";
                case GO:
                    return "You are being attacked! You must escape before you can go anywhere!";
                case LOOK:
                    return "You are being attacked! You can't look away!";
                default:
                    return String.format("You are being attacked! You don't have time to %s anything!", command.name());
            }
        }else{ //Player is exploring
            switch(command){
                case ATTACK_ENEMY:
                    return "What did you want to attack?";
                default:
                    return String.format("You're not in combat! There's nothing to %s!", command.name());
            }
        }
    }
}
