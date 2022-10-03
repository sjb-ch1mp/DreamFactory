package GameEngine.Exceptions;

import GameEngine.Action;
import Story.Command;

public class NoValidTargetException extends Exception{
    private Action action;
    private boolean targetExists;

    public NoValidTargetException(Action action, boolean targetExists){
        this.action = action;
        this.targetExists = targetExists;
    }

    public String toString(){
        if(targetExists){
            return String.format("You can't %s %s.", action.getCommand().name(), action.getTarget());
        }else{
            if(action.getCommand() == Command.SAY || action.getCommand() == Command.USE){
                return String.format("You %s, but nothing happens", action.toString());
            }else if(action.getCommand() == Command.GO || action.getCommand() == Command.LOOK){
                return String.format("%s is not a valid direction.", action.getTarget());
            }else if(action.getCommand() == Command.GREET){
                return String.format("There is noone here called '%s'", action.getTarget());
            }else if(action.getCommand() == Command.ATTACK || action.getCommand() == Command.SEARCH){
                return String.format("There is nothing here called '%s'", action.getTarget());
            }else if(action.getCommand() == Command.INSPECT || action.getCommand() == Command.USE){
                return String.format("You can't find a '%s'", action.getTarget());
            }else{
                return String.format("You cannot %s '%s'", action.getCommand().name(), action.getTarget());
            }
        }
        
    }
}
