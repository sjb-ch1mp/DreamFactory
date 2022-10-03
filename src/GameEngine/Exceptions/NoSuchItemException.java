package GameEngine.Exceptions;

import GameEngine.Action;

public class NoSuchItemException extends Exception{
    
    Action action;

    public NoSuchItemException(Action action){
        this.action = action;
    }

    public String toString(){
        return String.format("You do not have anything called '%s' in your inventory!", action.getTarget());
    }
}
