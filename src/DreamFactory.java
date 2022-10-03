import java.io.IOException;

import GameEngine.Consequence;
import GameEngine.GameEngine;
import GameEngine.GameHistory;
import GameEngine.Exceptions.NoSavedGameException;
import StoryParser.Parser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The DreamFactory class is the main application and user interface.
 * 
 * @author Samuel J. Brookes (u5380100)
 */
public class DreamFactory extends Application{

    //static 
    private static String TITLE = "DreamFactory";
    private static double INITIAL_HEIGHT = 500.0;
    private static double INITIAL_WIDTH = 750.0;
    
    //game components
    private GameEngine gameEngine;
    //StoryParser storyParser; TODO: Anqi

    //ui components
    private Label storyName;
    private Label currentHealth;
    private Label currentAttackPower;
    private Label currentDefence;
    private TextArea storyViewer;
    private TextField commandInput; //Primary game loop in this component bc the user input is the trigger
    private MenuBar mainMenu;

    /**
     * The main() function for the application 
     * @param args
     */
    public static void main(String args[]){
        launch(args);
    }

    /**
     * The Application.start() function. This function initializes
     * the components of the UI that are updated by the GameEngine
     * as global variables, initializes the GameEngine and then
     * establishes the main game loop.
     */
    public void start(Stage primaryStage) throws Exception{

        // Initialize UI components
        storyName = new Label();
        currentHealth = new Label();
        currentAttackPower = new Label();
        currentDefence = new Label();
        storyViewer = new TextArea();
        commandInput = new TextField(); //Primary game loop in this component bc the user input is the trigger
        mainMenu = buildMainMenu();
        Scene gamePortal = buildGamePortal();

        //Initialize game engine and set initial values
        initializeGameEngine();

        //Set up main game loop
        setUpGameLoop();

        //Format the game portal
        commandInput.requestFocus();
        primaryStage.setScene(gamePortal);
        primaryStage.setTitle(TITLE);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    /**
     * The initializeGameEngine() function resets all the main
     * components of the user interface with the initial values
     * from the story.
     */
    private void initializeGameEngine(){
        try{
            gameEngine = new GameEngine(Parser.parse("res/story/story.json")); //TODO: Anqi
            //gameEngine = new GameEngine(new TestStory(10).getTestStory()); //FIXME
            Consequence introduction = gameEngine.startStory();
            storyName.setText(gameEngine.getGameState().getStory().getName());
            storyViewer.setText(String.format("%s\n\n", introduction.getConsequenceDescription()));
            currentHealth.setText("" + introduction.getCurrentHeroHealth());
            currentAttackPower.setText("" + introduction.getCurrentHeroAttackPower());
            currentDefence.setText("" + introduction.getCurrentHeroDefence());
        }catch(IOException e){
            storyName.setText("Something went wrong!");
            storyViewer.setText(String.format("> IO EXCEPTION\n\n%s", e.toString()));
        }
    }

    /**
     * The buildMainMenu() function creates the necessary event handlers
     * for the menu items.
     * 
     * @return (MenuBar) the Options menu for the interface
     */
    private MenuBar buildMainMenu(){

        MenuItem startGame = new MenuItem("Start New Game");
        startGame.setOnAction((event) -> {
            initializeGameEngine();
        });

        MenuItem loadGame = new MenuItem("Load Saved Game");
        loadGame.setOnAction((event) -> {
            loadSavedGame();
        });

        MenuItem saveGame = new MenuItem("Save Current Game");
        saveGame.setOnAction((event) -> {
            gameEngine.saveGame();
            storyViewer.appendText("> GAME SAVED\n\n");
        });

        MenuItem saveAndQuitGame = new MenuItem("Save Current Game & Quit");
        saveAndQuitGame.setOnAction((event) -> {
            gameEngine.saveGame();
            Platform.exit();
        });

        MenuItem quitGame = new MenuItem("Quit");
        quitGame.setOnAction((event) -> {
            Platform.exit();
        });

        Menu mainMenu = new Menu("Options");
        mainMenu.getItems().addAll(
            new MenuItem[]{
                startGame,
                loadGame,
                saveGame,
                new SeparatorMenuItem(),
                saveAndQuitGame,
                quitGame
            }
        );
        
        return new MenuBar(mainMenu);
    }

    /**
     * The loadSavedGame() function calls GameEngine.loadGame() and
     * renders the results to the user interface.
     */
    private void loadSavedGame(){
        storyViewer.appendText("> LOADING SAVED GAME\n\n");
        try{
            GameHistory gameHistory = gameEngine.loadGame();
            storyViewer.setText("> LOADED SAVED GAME\n\n");
            for(int i=0; i<gameHistory.size(); i++){
                storyViewer.appendText(String.format("> %s\n\n", gameHistory.getAction(i).toString()));
                storyViewer.appendText(String.format("%s\n\n", gameHistory.getConsequence(i).getConsequenceDescription().trim()));
            }
            currentHealth.setText("" + gameEngine.getGameState().getHero().getHealth());
            currentAttackPower.setText("" + gameEngine.getGameState().getHeroAttackPower());
            currentDefence.setText("" + gameEngine.getGameState().getHeroDefence());
        }catch(NoSavedGameException e){
            storyViewer.appendText(String.format("%s\n\n", e.toString()));
        }   
    }

    /**
     * The setUpGameLoop() function creates the primary event handler
     * that takes user input, passes it to the GameEngine and renders
     * the results in the user interface.
     */

    private void setUpGameLoop(){
        commandInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    String userInput = commandInput.getText().trim();
                    if(userInput != null && userInput.length() > 0){
                        Consequence consequence = gameEngine.sendCommand(userInput);
                        storyViewer.appendText(String.format("> %s\n\n", consequence.getActionText()));
                        storyViewer.appendText(String.format("%s\n\n", consequence.getConsequenceDescription().trim()));
                        currentHealth.setText("" + consequence.getCurrentHeroHealth());
                        currentAttackPower.setText("" + consequence.getCurrentHeroAttackPower());
                        currentDefence.setText("" + consequence.getCurrentHeroDefence());
                        storyViewer.setScrollTop(Double.MAX_VALUE);

                        commandInput.setText("");
                    }
                }
            }
        });
    }

    /**
     * The buildGamePortal() function formats all user interface components 
     * in accordance with the values saved in the dreamfactory.css file and 
     * then builds the Scene for the user interface.
     * 
     * @return (Scene) the scene for the user interface
     */
    private Scene buildGamePortal(){
        
        //Dimensions
        double LABEL_HEIGHT = INITIAL_HEIGHT / 10;

        //Create static labels and styled frames - these need no further referencing
        Label healthTitle = new Label("Health:");
        Label apTitle = new Label("Attack Power:");
        Label defenceTitle = new Label("Defence:");
        Label statDividerOne = new Label("||");
        Label statDividerTwo = new Label("||");
        Label commandPrompt = new Label(">");
        HBox commandPortal = new HBox();
        VBox storyPortal = new VBox(); //holds the storyView and commandInput
        HBox statViewer = new HBox();
        VBox root = new VBox();

        //Establish CSS classes for all components
        root.getStyleClass().add("game-portal-main");
        storyName.getStyleClass().add("story-name");
        healthTitle.getStyleClass().add("stat-label");
        currentHealth.getStyleClass().add("stat-indicator");
        apTitle.getStyleClass().add("stat-label");
        currentAttackPower.getStyleClass().add("stat-indicator");
        defenceTitle.getStyleClass().add("stat-label");
        currentDefence.getStyleClass().add("stat-indicator");
        statDividerOne.getStyleClass().addAll("stat-label", "stat-divider");
        statDividerTwo.getStyleClass().addAll("stat-label", "stat-divider");
        statViewer.getStyleClass().add("stat-portal");
        storyPortal.getStyleClass().add("story-portal");
        storyViewer.getStyleClass().add("story-viewer");
        commandInput.getStyleClass().add("command-input");
        commandPrompt.getStyleClass().add("story-name");
        commandPortal.getStyleClass().add("command-portal");

        //Jigsaw it all together
        // -- Story Name
        storyName.setPrefHeight(LABEL_HEIGHT);
        // -- Stat Viewer
        statViewer.setPrefHeight(LABEL_HEIGHT);
        statViewer.setAlignment(Pos.CENTER);
        statViewer.getChildren().addAll(
            healthTitle, 
            currentHealth, 
            statDividerOne,
            apTitle,
            currentAttackPower,
            statDividerTwo,
            defenceTitle,
            currentDefence
        );
        statViewer.setPadding(new Insets(0.0, 80.0, 0.0, 80.0));
        statViewer.setSpacing(10.0);
        // -- Story Portal
        storyPortal.setPrefHeight(INITIAL_HEIGHT - LABEL_HEIGHT * 2);
        commandPrompt.setPrefWidth(INITIAL_WIDTH / 25);
        commandInput.setPrefHeight(LABEL_HEIGHT);
        commandInput.setPrefWidth(INITIAL_WIDTH - commandPrompt.getPrefWidth());
        commandPortal.getChildren().addAll(commandPrompt, commandInput);
        commandPortal.setHgrow(commandInput, Priority.ALWAYS);
        commandPortal.setAlignment(Pos.CENTER);
        storyViewer.setPrefHeight(INITIAL_HEIGHT - LABEL_HEIGHT * 3);
        storyViewer.setWrapText(true);
        storyViewer.setEditable(false);
        // -- root
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(mainMenu, storyName, statViewer, storyViewer, commandPortal);
        root.setVgrow(storyViewer, Priority.ALWAYS);

        Scene gamePortal = new Scene(root, INITIAL_WIDTH, INITIAL_HEIGHT);
        gamePortal.getStylesheets().add(getClass().getResource("dreamfactory.css").toExternalForm());
        return gamePortal;
    }   
}
