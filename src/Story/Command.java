package Story;


/**
 * This Enum class contains the valid command constants the user can type into the GUI. Each command phrase is
 * associated with a 'Mode' -- EXPLORATION, COMBAT, ANY. This is to ensure that unreasonable commands are not entered
 * at different stages of the game.
 *
 * e.g. While in Mode.COMBAT, the user should not be able to GREET the enemy.
 *
 * @author Shafin Kamal
 * @author Samuel Brookes
 *
 */
public enum Command  implements java.io.Serializable{

    /**
     * These are the following commands that can be parsed.
     */
    GREET(CommandTarget.CHARACTER, Mode.EXPLORATION),
    ATTACK(CommandTarget.ENEMY, Mode.EXPLORATION),
    SEARCH(CommandTarget.CONTAINER, Mode.EXPLORATION),
    USE(CommandTarget.ITEM, Mode.EXPLORATION),
    INSPECT(CommandTarget.ITEM, Mode.EXPLORATION),
    GO(CommandTarget.DIRECTION, Mode.EXPLORATION),
    LOOK(CommandTarget.DIRECTION, Mode.EXPLORATION),
    EXPLORE(CommandTarget.ROOM, Mode.EXPLORATION),
    INVENTORY(CommandTarget.HERO, Mode.EXPLORATION),
    SAY(CommandTarget.NONE, Mode.EXPLORATION),
    BLOCK(CommandTarget.ENEMY, Mode.COMBAT),
    ATTACK_ENEMY(CommandTarget.ENEMY, Mode.COMBAT),
    DODGE(CommandTarget.ENEMY, Mode.COMBAT),
    ESCAPE(CommandTarget.ENEMY, Mode.COMBAT),
    HELP(CommandTarget.NONE, Mode.ANY);

    /**
     * Variables
     */
    private final CommandTarget commandTarget;
    private final Mode mode;

    /**
     * Command constructor using the above variables
     * @param commandTarget - The target that the command should be directed to.
     * @param mode - The mode in which the command is valid.
     */
    Command(CommandTarget commandTarget, Mode mode) {
        this.commandTarget = commandTarget;
        this.mode = mode;
    }

    /**
     * Get the mode of a command. Used to ensure command-mode validity
     *
     * @author Samuel Brookes
     * @return Mode of THIS command.
     */
    public Mode getValidMode(){
        return mode;
    }

    /**
     * Isolate the commandTarget of THIS command.
     * @author Samuel Brookes.
     * @return CommandTarget of THIS command.
     */
    public CommandTarget getValidCommandTarget(){
        return commandTarget;
    }
}
