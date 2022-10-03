package SaveLoad;

import GameEngine.GameState;

import java.io.*;

/**
 * This class is to save and load current game status.
 * With the use of serialization.
 *
 * @author Yanyan Liu (u7189727)
 */
public class SaveNLoad implements Serializable{
    static GameState gameState;

    public static void saveGame(GameState gameState){
        try {
            // creates a file to put all game records in
            File file = new File("res/dreamfactory.sav");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(gameState);
            objectOutputStream.flush();
            objectOutputStream.close();
            System.out.println("Game saved.");
        } catch (FileNotFoundException e) {
            System.out.println("Game not saved." + e.getClass() + ": " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public GameState loadGame(){
        try{
            // load the directory of the file where all game records are saved.
            FileInputStream fileInputStream = new FileInputStream("res/dreamfactory.sav");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            gameState = (GameState) objectInputStream.readObject();
            objectInputStream.close();
            System.out.println("Game loaded.");
            return gameState;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gameState;
    }

}
