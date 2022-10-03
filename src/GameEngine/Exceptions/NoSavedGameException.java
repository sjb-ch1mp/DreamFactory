package GameEngine.Exceptions;

public class NoSavedGameException extends Exception{

    public String toString(){
        return "There is no save file to load!";
    }
    
}
