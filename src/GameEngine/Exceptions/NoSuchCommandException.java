package GameEngine.Exceptions;

public class NoSuchCommandException extends Exception {
    
    String message;

    public NoSuchCommandException(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        return message;
    }
}
